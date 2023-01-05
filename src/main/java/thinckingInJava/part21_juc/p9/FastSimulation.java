package thinckingInJava.part21_juc.p9;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chen
 * @date 2020/9/6 16:00
 * @Description
 */
public class FastSimulation {
    static final AtomicInteger integer = new AtomicInteger(100);
//    static Lock lock = new ReentrantLock();
    static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    static Lock lock = reentrantReadWriteLock.readLock();

    static class Evolver implements Runnable {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    lock.lock();
                    int oldValue = integer.get();
                    int next = oldValue + 1;
                    if (!integer.compareAndSet(oldValue, next)) {
                        System.out.println(" 旧值已经被并发的修改 " + oldValue);
                    }
                } finally {
                    lock.unlock();
                }

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0,50, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < 2; i++) {
            executor.execute(new Evolver());
        }
        TimeUnit.SECONDS.sleep(30);
        executor.shutdownNow();
        System.out.println("关闭线程池");
    }
}
