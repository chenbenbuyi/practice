package thinckingInJava.part21_juc.p2;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/4/1 7:43
 * @Description 线程优先级
 */
public class ThreadPriority implements Runnable {

    // ① 保证该变量对所有的线程可见，即当一个线程修改了变量值，其它的线程可以立即得知 但它也并不能保证并发条件下的安全性 volatitle修饰的变量读操作和普通变量几乎没有差别,写操作慢一些,比锁的开销低
    // ② 禁止指令重排，不进行任何的编译器优化
    // 在Java内存模型中，允许编译器和处理器对指令进行重排序，重排序过程不会影响到单线程程序的执行，却会影响到多线程并发执行的正确性
    // ③ volatitle 并不能保证操作的原子性
    private volatile double d;
    private int countDown = 5;
    private Integer priority;

    public ThreadPriority(Integer priority) {
        this.priority = priority;
    }


    @Override
    public String toString() {
        return "ThreadName = " + Thread.currentThread().getName() + " { priority=" + priority +
                '}';
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(priority);
        while (true) {
            for (int i = 0; i < 10000; i++) {
                d += (Math.PI + Math.E) / i;
                //取模运算 ；求余（取模）有个规律就是：左边小于右边，结果为左边，左边大于右边，看余数
                if (i % 1000 == 0)
                    // 模拟在长耗时的任务执行中，线程不断地让步，让cpu选择的从其它线程执行计算任务，即可发现，优先级高的，获得的执行次数要多很多
                    Thread.yield();
            }

            System.out.println(this);
            if (countDown-- == 0)
                return;
        }
    }


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 10, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
            executor.execute(new ThreadPriority(Thread.MIN_PRIORITY));
        }
        executor.execute(new ThreadPriority(Thread.MAX_PRIORITY));
        executor.shutdown();
    }
}
