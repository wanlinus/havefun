package cn.wanli.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 解决了雪崩
 * 每个节点订阅前一个节点的watch事件
 *
 * @author wanli
 * @date 2019-08-30 11:39
 */
public class ZkDistributeImproveLock implements Lock {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkDistributeImproveLock.class);

    private ZkClient client;
    /**
     * 父节点
     */
    private String lockPath;

    /**
     * 自己节点
     */
    private ThreadLocal<String> currentPath = new ThreadLocal<>();

    /**
     * 排在我前面的节点
     */
    private ThreadLocal<String> beforePath = new ThreadLocal<>();

    public ZkDistributeImproveLock(ZkClient client, String lockPath) {
        this.client = client;
        this.lockPath = lockPath;

        if (!client.exists(lockPath)) {
            client.createPersistent(lockPath);
        }
    }

    @Override
    public void lock() {
        if (!tryLock()) {
            waitForLock();
            lock();
        }
    }

    private void waitForLock() {
        CountDownLatch cdl = new CountDownLatch(1);
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                LOGGER.info("=============zk节点[{}]被删除===========================", dataPath);
                cdl.countDown();
            }
        };
        client.subscribeDataChanges(beforePath.get(), listener);

        if (client.exists(beforePath.get())) {
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        client.unsubscribeDataChanges(beforePath.get(), listener);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {

        if (this.currentPath.get() == null || !client.exists(this.currentPath.get())) {
            String node = client.createEphemeralSequential(lockPath + "/", "Locked");
            currentPath.set(node);
        }
        List<String> children = client.getChildren(lockPath);

        Collections.sort(children);

        //当前节点是最小的就获得锁
        if (currentPath.get().equals(lockPath + "/" + children.get(0))) {
            return true;
        } else {
            //不是最小的就获取前面一个节点, 订阅他
            int before = children.indexOf(currentPath.get().substring(lockPath.length() + 1));
            beforePath.set(lockPath + "/" + children.get(before - 1));
        }

        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        LOGGER.info("释放分布式锁 {}", currentPath.get());
        client.delete(currentPath.get());
        currentPath.remove();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
