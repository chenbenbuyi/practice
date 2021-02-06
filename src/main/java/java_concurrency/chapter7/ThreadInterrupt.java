package java_concurrency.chapter7;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/2/1 22:37
 * @Description 中断线程的正确处理方式
 */

public class ThreadInterrupt{
    /**
     *  该测试发起中断却并没有中断阻塞队列的put方法，任务并没有停止，jvm也没有退出，why? (注意：没中断的情况线程创建代码都为 代码 1 中所示情况)
     */
    public static void main(String[] args) throws InterruptedException {
        testInterrupt2();
    }

    private static void testInterrupt1() throws InterruptedException {
        PrimeProducer primeProducer = new PrimeProducer(new ArrayBlockingQueue<>(3)," 队列阻塞线程 ");
        /**
         *  这里注释掉的方式其实是开启的外层线程，和primeProducer线程对象本身是不同。参见 {@link ThreadCreateTest} 的测验
         */
//        new Thread(primeProducer).start();  // 代码 1
        primeProducer.start();                // 代码 2
        // 该线程在5秒之后对任务发起中断
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                primeProducer.cancel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        while (!primeProducer.isInterrupted()){
            TimeUnit.SECONDS.sleep(1);
            System.out.println("未检测到线程"+primeProducer.getName()+"发生中断");
        }
        System.out.println("监测到线程任务发生中断,程序结束");
    }


    private static void testInterrupt2() throws InterruptedException {
        SleepThread thread = new SleepThread(" sleep阻塞线程 ");
//        new Thread(thread).start();   // 代码 1
        thread.start();                 // 代码 2
        // 该线程在5秒之后对任务发起中断
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                thread.cancel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        while (!thread.isInterrupted()){
            TimeUnit.SECONDS.sleep(1);
            System.out.println("未检测到线程"+thread.getName()+"发生中断");
        }
        System.out.println("监测到线程任务发生中断,程序结束");
    }
}

 class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue,String name) {
        this.queue = queue;
        super.setName(name);
    }

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!Thread.currentThread().isInterrupted()){
            try {
                System.out.println("像队列中放入元素："+p);
                queue.put(p=p.nextProbablePrime());
            } catch (InterruptedException e) {
                e.printStackTrace();
                /**
                 *  阻塞队列响应中断并抛出异常后，可以在此根据业务需求进行必要的的收尾工作
                 *  通常情况下，都不应该catch该异常后不做任何处理，更通用的做法是恢复中断状态，让该线程的所有者在监测到中断状态时执行相关的中断策略——当发现中断请求时，需要做哪些工作
                 *   Thread.currentThread().interrupt();
                 */
            }
        }
        System.out.println("线程任务已经中断执行");
    }

    /**
     *  调用中断方法触发阻塞队列的中断异常，从而使线程不至于无限时阻塞，能够响应中断并退出任务执行
     */
    public void cancel(){
        System.out.println("执行任务中断，"+getName()+"线程的中断状态："+isInterrupted());
        interrupt();
        System.out.println("执行任务中断，"+getName()+"线程的中断状态："+isInterrupted());
    }
}


class SleepThread extends Thread {

    public SleepThread(String name) {
        super.setName(name);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("线程任务已经中断执行");
    }
    public void cancel(){
        System.out.println("执行任务中断，"+getName()+"线程的中断状态："+isInterrupted());
        interrupt();
        System.out.println("执行任务中断，"+getName()+"线程的中断状态："+isInterrupted());
    }
}
