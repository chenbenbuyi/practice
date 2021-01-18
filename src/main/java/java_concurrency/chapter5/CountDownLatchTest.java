package java_concurrency.chapter5;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/1/18 21:50
 * @Description 闭锁测试——利用闭锁来实现计算多个线程任务执行完成需要的时间总时间
 * 要求多个线程任务同时开启执行，规避手动开启线程任务的时间先后差异（比如先启动的线程大概率先执行，测试线程数量的活跃性随着先后启动时间递减，失去了并发的特性等）
 * 另一个闭锁示例参见{@link thinckingInJava.part21_juc.p7.CountDownLatchTest}
 */

@Slf4j
public class CountDownLatchTest {

    private static final int THREADS = 10;

    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random(100);
        long time = timeTasks(THREADS, () -> {
            try {
                // 任务随机执行时长
                TimeUnit.SECONDS.sleep(rand.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        log.info("{}个并发线程执行总时长：{}", THREADS, time);
    }


    public static long timeTasks(int nThreads, Runnable task) throws InterruptedException {
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        endGate.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        long start = System.nanoTime();
        startGate.countDown(); // 全体都有：预备，go !
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }
}
