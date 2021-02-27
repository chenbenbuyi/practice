package java_concurrency.chapter8_threadpool;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chen
 * @date 2021/2/27 14:14
 * @Description ThreadPoolExecutor 提供的几个扩展方法实现统计计数等功能
 */
@Slf4j
public class TimingThreadPool extends ThreadPoolExecutor {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final AtomicLong numsTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();
    private final DecimalFormat df = new DecimalFormat("0.00");

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    /**
     * @param t 执行任务的线程
     * @param r 任务
     *          根据javadoc的描述，可以用来重置本地线程变量或者日志记录
     * 。。。and may be used to re-initialize ThreadLocals, or to perform logging.
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.info("beforeExecute {} 开启任务 {}", t.getName(), r.hashCode());
        startTime.set(System.nanoTime());
    }

    /**
     *  该方法会被执行任务的线程执行
     * 无论任务正常返回还是抛出异常（任务抛出的是Error除外），都会回调该方法
     * 如果beforeExecute先抛出异常，任务将不被执行，afterExecute也不会调用
     * @param r
     * @param t  t the exception that caused termination,the Throwable is the uncaught {@code RuntimeException} r {@code Error} that caused execution to terminate abruptly.
     *          导致线程任务执行终止的 运行时异常或Error
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            numsTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            log.info(" afterExecute 任务 {} 执行完毕，执行时长：{} 秒", r, df.format((float) taskTime / 1000000000));
            if (t != null) {
                log.error(t.getMessage());
            }
        } finally {
            super.afterExecute(r, t);
        }
    }

    /**
     * 线程池完成关闭操作时调用——任务完成且工作者线程也关闭
     */
    @Override
    protected void terminated() {
        try {
            log.info("运行终止，平均耗时：{} 秒", df.format((totalTime.get() / numsTasks.get()) / 1000000000f));
        } finally {
            super.terminated();
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        ThreadPoolExecutor exec = new TimingThreadPool(1, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1), new MyThreadFactory("自定义线程池"));
        for (int i = 0; i < 5; i++) {
            exec.submit(() -> {
                try {
                    int rand = random.nextInt(5) + 1;
                    if (rand == 3){
                        throw new RuntimeException("自抛异常");
                    }
                    TimeUnit.SECONDS.sleep(rand);
                } catch (InterruptedException e) {
                }
            });
        }
        exec.shutdown();
    }
}
