package java_concurrency.extra;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chen
 * @date 2021/6/27 16:56
 * @Description 读写锁测试——读读共享，读写或写写互斥
 */
public class ReentrantReadWriteLockTest {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();
    Random random = new Random(3);
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLockTest test = new ReentrantReadWriteLockTest();
        /**
         *  测试会发现，多个线程可以同时获取到读锁进行线程任务，但是当有写锁时，则只能一个线程获取并释放后，其它的读写线程才能继续获取锁
         */
        for (int i = 1; i < 6; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                    Thread.yield();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.write();
            }, "线程：" + i).start();
        }
        for (int i = 6; i < 11; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.read();
            }, "线程：" + i).start();
        }

        latch.countDown();
        System.out.println("线程开闸。。。。。。。。。。。。。。。。。");
    }

    @SneakyThrows
    public void write() {
        int time = random.nextInt(3);
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 线程获取到写锁,执行任务时长：" + time);
            TimeUnit.SECONDS.sleep(time);
        } finally {
            System.out.println(Thread.currentThread().getName() + " 线程释放写锁");
            writeLock.unlock();
        }
    }

    @SneakyThrows
    public void read() {
        int time = random.nextInt(4);
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 线程获取到读锁,执行任务时长：" + time);
            TimeUnit.SECONDS.sleep(time);
        } finally {
            System.out.println(Thread.currentThread().getName() + " 线程释放读锁");
            readLock.unlock();
        }
    }
}
