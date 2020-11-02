package thinckingInJava.part21_juc.p3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chen
 * @date 2020/9/9 7:00
 * @Description 共享锁和独享锁
 */
public class ReentrantLockTest {
    public static void main(String[] args) {
        SharedLock sharedLock = new SharedLock();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                sharedLock.read();
            }).start();
        }
    }
}


class SharedLock {
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    long start = System.currentTimeMillis();

    void read() {
        try {
            readLock.lock();
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
        System.out.println("执行时长：" + (System.currentTimeMillis() - start));
    }
}
