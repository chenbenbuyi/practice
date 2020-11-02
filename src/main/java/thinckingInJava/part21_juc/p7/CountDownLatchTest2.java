package thinckingInJava.part21_juc.p7;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/26 10:09
 * @Description
 */
public class CountDownLatchTest2 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchTest2 countDownLatchTest2 = new CountDownLatchTest2();
        countDownLatchTest2.timeTasks(2,new Thread(new My()));
    }




    static class My implements Runnable{
        @Override
        public void run() {
            System.out.println("传入的额线程任务开始执行。。。。。5");
        }
    }

    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(() -> {
                try {
                    // 导致所有线程等待
                    System.out.println("当前线程等着 2");
                    startGate.await();
                    System.out.println("所有线程被唤醒啦 4");
                    try {
                        task.run();
                    } finally {
                        System.out.println("传入的线程finally 6");
                        endGate.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("会执行到这里 1");
            t.start();
        }
//        System.currentTimeMillis() 返回的毫秒，这个毫秒其实就是自1970年1月1日0时起的毫秒数.
//        System.nanoTime提供相对精确的计时，但是不能用他来计算当前日期
        TimeUnit.MILLISECONDS.sleep(300);
        long startTime = System.nanoTime();
        // 减少锁存器的计数，这里执行一次即归零
        startGate.countDown();
        // 当所有线程开始执行任务后， 3 和 4的先后是不确定的，因为竞态条件，取决于谁占有运行时间片
        System.out.println("传入的线程任务开始等待 3");
        endGate.await();
        System.out.println("传入的线程任务执行完毕 7");
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
