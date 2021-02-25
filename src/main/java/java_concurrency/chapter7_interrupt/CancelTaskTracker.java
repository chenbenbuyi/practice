package java_concurrency.chapter7_interrupt;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author chen
 * @date 2021/2/24 19:08
 * @Description 该类的结构可以获取线程池关闭时哪些正在执行的任务未执行完的
 *  exec.shutdownNow() 会让线程池强行关闭，但会返回已提交但未开始执行的任务，对于已经开始的任务，则被忽略
 *  关于 isTerminated 方法,若线程池关闭后所有任务都已完成(通过shutdownNow虽然不一定所有任务都完成，有的任务可能被中断，但是也要任务完全停止后才返回true,结合awaitTermination一起判断)，则返回true。注意——除非首先调用shutdown或shutdownNow，否则isTerminated永不为true。
 */

@Slf4j
public class CancelTaskTracker extends ThreadPoolExecutor {
    private final Set<Runnable> tasksCancelledAtShutdown = Collections.synchronizedSet(new HashSet<>());

    public CancelTaskTracker(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory factory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, factory);
    }

    public List<Runnable> getCncelledTasks() {
        if (!super.isTerminated()) {
            throw new IllegalStateException("线程池尚有任务未执行完");
        }
        return new ArrayList<>(tasksCancelledAtShutdown);
    }

    @Override
    public void execute(final Runnable runnable) {
        super.execute(() -> {
            try {
                runnable.run();
            } finally {
                // 跟踪源码你会发现shutdownNow 就是通过 interrupt 来中断线程任务的
                if (isShutdown() && Thread.currentThread().isInterrupted()) {
                    System.out.println("添加尚未执行完的线程任务，其哈希值为：" + runnable.hashCode());
                    tasksCancelledAtShutdown.add(runnable);
                }
            }
        });
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        return super.submit(() -> {
            try {
                runnable.run();
            } finally {
                /**
                 *  isShutDown：当调用shutdown()或shutdownNow()方法后返回为true。
                    isTerminated：当调用shutdown()方法后，并且所有提交的任务完成后返回为true;
                    isTerminated：当调用shutdownNow()方法后，成功停止后返回为true;
                    如果线程池任务正常完成，都为false
                 */
                if (isShutdown() && Thread.currentThread().isInterrupted()) {
                    System.out.println("添加未执行完的线程任务：" + runnable.hashCode());
                    tasksCancelledAtShutdown.add(runnable);
                }
            }
        });
    }


    public static void main(String[] args) throws InterruptedException {
        /**
         *  几种创建线程池线程名的方式
         *  CustomizableThreadFactory—Spring 框架
         *  {@link ThreadFactoryBuilder}—Google guava 工具类
         *  {@link BasicThreadFactory}—Apache commons-lang3
         *  {@link cn.hutool.core.thread.ThreadFactoryBuilder} --hutool 工具类
         */
        ThreadFactory basicThreadFactory = new BasicThreadFactory.Builder().namingPattern("lang3-%s").build();
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("guava-%s").build();
        ThreadFactory hutoolThreadFactory = cn.hutool.core.thread.ThreadFactoryBuilder.create().setNamePrefix("hutool-").build();
        CancelTaskTracker exec = new CancelTaskTracker(4, 10, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), hutoolThreadFactory);
        for (int i = 0; i < 10; i++) {
            Runnable anonymous = new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.info("{} 发生中断，线程对象哈希值：{}", Thread.currentThread().getName(), Thread.currentThread().hashCode());
                    }
                }
            };

            /**
             *  惊讶的发现，lambda 表达式写法，创建的10个对象其哈希值竟然是一样的
             */
            Runnable lambda = () -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.info("{} 发生中断，线程对象哈希值：{}", Thread.currentThread().getName(), Thread.currentThread().hashCode());
                }
            };
//            System.out.println("哈希值：" + lambda.hashCode());
            exec.execute(anonymous);
        }
        /**
         *  任务执行需要5秒中才能完成，3秒的时候就关闭线程池，根据线程池设置，意味着会有6个线程任务被中断，有4个任务在队列中被返回
         */
        TimeUnit.SECONDS.sleep(3);
        log.info("强行关闭线程池时，{} 个线程任务尚未执行！", exec.shutdownNow().size());
        /**
         *  awaitTermination 会阻塞主线程直到调用shutdown后所有任务都已经完成，或者等待超时，或者当前线程发生中断 。通常和shutdown一起使用
         *  @return {@code true} if this executor terminated and {@code false} if the timeout elapsed before termination
         *  这里需要做稍微的等待，线程池才会正真的停止
         */
        if (exec.awaitTermination(1, TimeUnit.MILLISECONDS))
            log.info("线程池关闭后，获取到的被中断的任务数：{}", exec.getCncelledTasks().size());
    }
}
