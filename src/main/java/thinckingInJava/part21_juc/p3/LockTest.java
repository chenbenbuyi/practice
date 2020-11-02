package thinckingInJava.part21_juc.p3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chen
 * @date 2020/4/28 6:59
 * @Description
 */
public class LockTest {

    private ReentrantLock lock = new ReentrantLock();

    public void untimed() {
        boolean captured = lock.tryLock(); //在调用时没有被另一个线程占用，则获得锁
        try {
            System.out.println("tryLock():" + captured);
        } finally {
            if (captured)
                lock.unlock();
        }
    }

    public void timed() {
        boolean captured = false; //在调用时没有被另一个线程占用，则获得锁
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("tryLock(2, TimeUnit.SECONDS):" + captured);
        } finally {
            if (captured)
                lock.unlock();
        }
    }


    public static void main(String[] args) {
        final LockTest lockTest = new LockTest();
        lockTest.untimed();
        lockTest.timed();
        // 新建线程获取锁
        new Thread() {
            {
                setDaemon(true);
            }
            @Override
            public void run() {
                lockTest.lock.lock();
                System.out.println("捕获  acquired");

            }
        }.start();
        Thread.yield();
        lockTest.untimed();
        lockTest.timed();
    }
}
