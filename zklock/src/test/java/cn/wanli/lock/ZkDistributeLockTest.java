package cn.wanli.lock;

import cn.wanli.zk.ZkSerializerImpl;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @author wanli
 * @date 2019-08-30 10:45
 */
public class ZkDistributeLockTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkDistributeLockTest.class);

    private ZkClient client;

    @Before
    public void init() {
        client = new ZkClient("10.0.0.20:2181");
        client.setZkSerializer(new ZkSerializerImpl());
    }

    /**
     * 非公平锁测试
     */
    @Test
    public void flock() throws InterruptedException {
        long start = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();

        Runnable runnable = () -> {
            Lock lock = new ZkDistributeLock(client, "/wanlic");
            lock.lock();
            try {
                LOGGER.info("暂停100ms {}", Thread.currentThread().getName());
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        };
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }

        threads.stream().forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void glock() throws InterruptedException {
        long start = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();

        Runnable runnable = () -> {
            Lock lock = new ZkDistributeImproveLock(client, "/wanlid");
            lock.lock();
            try {
                LOGGER.info("暂停100ms {}", Thread.currentThread().getName());
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        };
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }

        threads.stream().forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
