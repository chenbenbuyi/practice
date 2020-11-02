package thinckingInJava.part21_juc.p7;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author chen
 * @date 2020/8/15 10:14
 * @Description 模拟测试 信号量
 */
public class MySemaphoreTest {

    private static final int SIZE = 5;
    private static final int SEM_SIZE = 3;

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 20, TimeUnit.SECONDS, new SynchronousQueue<>());
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        Semaphore sem = new Semaphore(SEM_SIZE, true);
        Random rand = new Random(47);
        for (int i = 0; i < SIZE; i++) {
            executor.execute(new MySemaphore(countDownLatch, sem));
        }
        // 随机时间减少闭锁的计数器的值
        for (int i = 0; i < SIZE; i++) {
            TimeUnit.SECONDS.sleep(rand.nextInt(3));
            countDownLatch.countDown();
        }
        executor.shutdown();
    }
}


class MySemaphore implements Runnable {

    private CountDownLatch countDownLatch;
    private Semaphore sem;

    public MySemaphore(CountDownLatch countDownLatch, Semaphore sem) {
        this.countDownLatch = countDownLatch;
        this.sem = sem;
    }

    @Override
    public void run() {
        try {
            // 线程等待
            System.out.println(Thread.currentThread().getName() + " 线程等待");
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName() + " 尝试获得执行许可");
            sem.acquire();
            System.out.println(Thread.currentThread().getName() + " 获得执行许可");
            TimeUnit.SECONDS.sleep(5);// 模拟任务执行时长
            System.out.println(Thread.currentThread().getName() + " 线程正在执行任务完成，释放信号量许可证");
            sem.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}