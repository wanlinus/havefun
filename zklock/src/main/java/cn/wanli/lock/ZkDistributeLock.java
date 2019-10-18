package cn.wanli.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author wanli
 * @date 2019-08-30 10:12
 */
public class ZkDistributeLock implements Lock {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkDistributeLock.class);

    private ZkClient client;

    private String lockPath;

    public ZkDistributeLock(ZkClient client, String lockPath) {
        this.client = client;
        this.lockPath = lockPath;
    }

    @Override
    public void lock() {
        if (!tryLock()) {
            waitForLock();
            //从等待中醒来, 继续获得锁
            lock();
        }
    }

    private void waitForLock() {
        CountDownLatch cdl = new CountDownLatch(1);

        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                LOGGER.info("准备抢锁");
                cdl.countDown();
            }
        };
        client.subscribeDataChanges(lockPath, iZkDataListener);
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.unsubscribeDataChanges(lockPath, iZkDataListener);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            client.createEphemeral(lockPath);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        client.delete(lockPath);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
