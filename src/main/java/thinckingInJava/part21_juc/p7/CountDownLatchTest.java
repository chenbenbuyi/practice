package thinckingInJava.part21_juc.p7;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/26 9:27
 * @Description 闭锁实现
 * 允许一个或多个线程等待直到在其他线程中执行的一组操作完成的后再执行
 */
public class CountDownLatchTest {
    static final int SIZE =10;
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        for (int i = 0; i <10 ; i++) {
            //模拟开启的10个线程任务，都因为CountDownLatch而等待，不执行
            executorService.execute(new WaitingTask(countDownLatch));
        }
        System.out.println("所有线程任务都进入等待！！！！！！！！！！");
        for (int i = 0; i < SIZE; i++) {
            executorService.execute(new MyTask(countDownLatch));
        }
        executorService.shutdown();
    }
}

class MyTask implements Runnable{

    private static int counter=0;
    private final int id = counter++;
    private static Random rand = new Random(47);
    private final CountDownLatch latch;

    public MyTask(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            doWork();
            // 减少锁存器的计数，如果计数达到零，释放所有等待的线程。
            long count = latch.getCount();
            System.out.println("当前计数值："+count);
            if(count==0)
                System.out.println("计数为0 ，线程任务开始执行");
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(200));
    }

    @Override
    public String toString() {
        return String.format("%1$-3d ",id);
    }
}

class WaitingTask implements Runnable{

    private static int counter=0;
    private final int id = counter++;
    private final CountDownLatch latch;

    public WaitingTask(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println(String.format("WaitingTask %1$-3d ",id)+" 开始等待。。");
            //导致当前线程等到锁存器计数到零，除非线程是 interrupted 。
            // 也就是说说当前线程任务会一直等待，直到CountDownLatch构造对象时传入的int值变为0
            // await(long timeout,TimeUnit unit) 类似方法，只不过如果等待的超时时间到了还没有计数为0，则等待线程开始执行
            latch.await();
            System.out.println("CountDownLatch 计数为0 了。。。。"+Thread.currentThread().getName()+" 线程执行任务");
        } catch (InterruptedException e) {
            System.out.println(this+"中断");
        }
    }

    @Override
    public String toString() {
        return String.format("WaitingTask %1$-3d ",id);
    }
}