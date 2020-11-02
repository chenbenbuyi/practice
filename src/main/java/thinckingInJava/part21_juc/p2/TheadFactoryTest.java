package thinckingInJava.part21_juc.p2;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/4/5 9:09
 * @Description ThreadFactory 可以定制由 Executor创建的线程的属性（后台、优先级、名称等）
 */
public class TheadFactoryTest implements ThreadFactory {


    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
//        thread.setDaemon(true);
        thread.setPriority(Thread.NORM_PRIORITY);
//        thread.setName("陈线程:"+new Date().getTime());
        return thread;
    }


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), new TheadFactoryTest(), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 5; i++) {
            executor.execute(new ThreadPriority(Thread.MIN_PRIORITY));
        }
        executor.shutdown();
    }
}
