package java_concurrency.extra;

import thinckingInJava.part21_juc.p2.TheadFactoryTest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @date: 2021/4/21 9:17
 * @author: chen
 * @desc: 探究：一般网文都说，线程池中，核心线程数没满的情况下，新来任务会创建新线程去处理。
 * 那么，如果新进任务是在已创建的线程执行完上一个任务之后被提交的，是会复用之前创建的线程还是会新建线程呢？
 * 实践表明：即使新任务提交时之前的核心线程空闲，在核心线程未满的情况下，依然是创建新线程来执行任务，而不是复用核心线程
 * 测试误区：以为 getActiveCount 获取的就是当前已创建的线程数量，其实getActiveCount获取的是当前线程池中正在执行任务的线程数量。
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,5, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), new TheadFactoryTest(), new ThreadPoolExecutor.AbortPolicy());
        System.out.println("任务提交前正在执行任务的线程数量："+executor.getActiveCount());
        System.out.println("任务提交前池对象："+executor.toString());
        executor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //
        System.out.println("第一次任务提交后正在执行任务的线程数量："+executor.getActiveCount());
        System.out.println("第一次任务提交后池对象："+executor.toString());
        // 延时确保第一个线程任务执行完成
//        TimeUnit.SECONDS.sleep(4);
        executor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("第二次任务提交后正在执行任务的线程数量："+executor.getActiveCount());
        System.out.println("第二次任务提交后池对象："+executor.toString());
    }
}
