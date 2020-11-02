package thinckingInJava.part21_juc.p3;

import thinckingInJava.part21_juc.p2.TheadFactoryTest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/4/13 22:46
 * @Description 偶数有效性检测类
 * 该测试项中明确两个问题：
 * ① 线程任务改变判断条件（比如判断非奇数或非偶数或者改变currentEventValue的初始值）,任务执行效果是不一样的
 * ② 采用不同的线程池，运行结果也不一样
 * ③ java 中，递增操作并不是原子性的
 * ④ count 值为 10 、最大线程数量不超过6的时候，为什么在循环中i=9的值没有出现？也就是为什么线程会挂起导致后续循环无法进行，test 方法无法结束退出？
 */

public class EvenChecker implements Runnable {

    private IntGenerator intGenerator;

    private final int id;

    public EvenChecker(IntGenerator intGenerator, int id) {
        this.intGenerator = intGenerator;
        this.id = id;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        while (!intGenerator.isCanceled()) {
            //  通过延长任务执行时间，增加cpu切换线程任务的几率，也就加大了数据不一致状态的几率，这个时候，任务是会停下来的
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int var = intGenerator.next();
//            System.out.println("线程 "+name+" 正在执行任务,当前next 返回值："+var);
            // 对应的两个二进制位都为1时，结果才为1
            if ((var % 2) != 0) {
                // 按照正常的执行逻辑，只可能打印一次，但在多线程并发执行的过程中，由于状态的不一致性，比如一个线程运行到next方法，由于yield调用，出现礼让，让另一个线程继执行，可这个时候，该线程任务已经进入判断
                System.out.println("当前线程：" + name + "中的数字 " + var + " 是奇数");
                intGenerator.cacel();
            }
        }
        System.out.println("线程 " + name + " 正在执行任务结束===================， !intGenerator.isCanceled() 的值为 " + !intGenerator.isCanceled());
    }

    /**
     * 四种线程池介绍：
     * newCachedThreadPool 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
     * 其内部队列为没有任何容量的同步队列 SynchronousQueue 如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程
     * 当任务数增加时，此线程池又可以智能的添加新线程来处理任务
     * newFixedThreadPool  一个定长线程池，可控制线程最大并发数，超出的线程任务会在队列中等待 队列长度为int最大值
     * <p>
     * newScheduledThreadPool 定长线程池，支持定时及周期性任务执行
     * newSingleThreadExecutor 只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行 如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它
     */


    /**
     * 测试发现：当前情况下，最大线程数量为6，触发线程池拒绝策略，这时很有意思的事情发生了，for循环第9次停住了，整个方法处于假死状态，why?
     * 这要从拒绝策略说起。
     * 线程池拒绝策略有四种：
     * AbortPolicy 丢弃任务并抛异常 会中断调用者的处理过程，所以除非有明确需求，一般不推荐
     * DiscardPolicy 丢弃任务不抛异常
     * CallerRunsPolicy  由调用线程自己处理该任务
     * DiscardOldestPolicy  丢弃最前面的任务，重新尝试提交被拒绝的任务
     * for循环过程中，由于抛出异常，导致运行中断。这其实就是简单的异常导致任务中断。 如下i==5自己抛出异常任务中断，也是一样的效果.这个时候，即使有线程超时时间，也不起作用
     * 解决办法，就是要么捕获异常，要么不使用抛出异常的拒绝策略。挥着强行设置allowCoreThreadTimeOut 参数为true,让核心线程退出
     *  区别：allowCoreThreadTimeOut 是让核心线程退出，而 keepAliveTime 是给核心线程外的空闲线程设置超时时间
     */

    public static void test(IntGenerator generator, int count) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 7, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), new TheadFactoryTest(), new ThreadPoolExecutor.AbortPolicy());
//        ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newCachedThreadPool(new TheadFactoryTest());
        // 多个线程共享一个generator对象
        for (int i = 0; i < count; i++) {
            EvenChecker evenChecker = new EvenChecker(generator, i);
            executor.execute(evenChecker);
//            executor.allowCoreThreadTimeOut(true);  强行设置核心线程退出
//            if (i == 5)
//                throw new RejectedExecutionException("Task " + evenChecker.toString());
            System.out.println("当前第（" + (i + 1) + "）次循环，队列中的等待线程任务数：" + executor.getQueue().size() + " ,线程池线程数量：" + executor.getPoolSize());
        }
        System.out.println("test 方法结束，队列中的等待线程任务数：" + executor.getQueue().size() + " ,线程池线程数量：" + executor.getPoolSize());
        executor.shutdown(); // 该方法使得
    }

    public static void main(String[] args) {
        EvenChecker.test(new IntGenerator.LockIntGenertor(), 10);
    }


}
