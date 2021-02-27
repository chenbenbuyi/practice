package java_concurrency.chapter8_threadpool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author chen
 * @date 2021/2/27 13:04
 * @Description 自定义线程工厂
 */
public class MyThreadFactory implements ThreadFactory {
    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new MyAppThread(r);
    }

    /**
     * 小测一下线程名字
     *  问：线程池拒绝策略后，还能继续提交任务吗？
     *  这其实是调用者自身的问题，如果你捕获了异常，那么主线程是可以继续提交的，否则，线程池已经抛出异常，没有捕获的话会直接导致方法的终止，不在处理后续提交逻辑
     *  同时，由于线程池向主线程抛出异常，主线程方法执行终止，后续代码无法执行，比如 shutdown写在最后也是无法执行到的
     */
    public static void main(String[] args) {
        ThreadPoolExecutor exec = new ThreadPoolExecutor(1, 3,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue(1), new MyThreadFactory("自定义线程池"), new ThreadPoolExecutor.AbortPolicy());
        Random random = new Random(50);
        for (int i = 0; i < 10; i++) {
            System.out.println("任务提交");
            try {
                exec.submit(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(random.nextInt(5));
//                        System.out.println(Thread.currentThread().getName()+" 任务执行完成！");
                    } catch (InterruptedException e) {

                    }
                });
            }catch (RejectedExecutionException e){
                e.printStackTrace();
            }
        }
        exec.shutdown();
    }
}
