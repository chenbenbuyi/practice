package java_concurrency.chapter8_threadpool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author chen
 * @date 2021/2/27 10:01
 * @Description 线程池场景中，工作队列被填满之后，通常会有饱和策略来拒绝任务。
 * 此示例实现的是当队里满时，通过信号量控制任务的提交，避免任务被无限制的提交到线程池。
 */
public class BoundedExecutor {
    private final Executor exec;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor exec, Semaphore semaphore) {
        this.exec = exec;
        this.semaphore = semaphore;
    }

    public void submitTask(Runnable runnable, Order order) throws InterruptedException {
        semaphore.acquire();
        try {
            System.out.println("提交的第" + order.getOrder() + "任务");
            exec.execute(() -> {
                try {
                    runnable.run();
                } finally {
                    semaphore.release();
                }
            });
        } catch (RejectedExecutionException e) {
            System.out.println("第 " + order.getOrder() + " 个任务触发线程池决绝策略");
//            semaphore.release();
        }
    }
    // 主要是为了计数用
    static class Order {
        private static int taskCount = 0;
        private final int order = ++taskCount;

        public int getOrder() {
            return order;
        }
    }

//    public static void main(String[] args) throws InterruptedException {
//        ThreadPoolExecutor exec = new ThreadPoolExecutor(1, 1,
//                0L, TimeUnit.MILLISECONDS,
//                new ArrayBlockingQueue(1), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
//        /**
//         *  利用信号量来限流，信号量的上限值通常设置为线程池大小加上队列容量
//         */
//        Random random = new Random(50);
//        Semaphore semaphore = new Semaphore(2);
//        BoundedExecutor boundedExecutor = new BoundedExecutor(exec, semaphore);
//        for (int i = 0; i < 10; i++) {
//            Order order = new Order();
//            try {
//                boundedExecutor.submitTask(() -> {
//                    try {
//                        TimeUnit.SECONDS.sleep(random.nextInt(5));
//                        System.out.println("第 " + order.getOrder() + " 个任务执行完成！");
//                    } catch (InterruptedException e) {
//                    }
//                }, order);
//            } catch (Error e) {
//                e.printStackTrace();
//            }
//        }
//        exec.shutdown();
//    }
//

    /**
     * 一定要注意拒绝策略的含义
     * 比如丢弃任务策略 DiscardPolicy ，会导致提交的任务直接被丢弃，如果提交的任务过快，线程池执行不过来，导致大量的任务被丢弃
     * 测试发现，如果策略 DiscardOldestPolicy 和 同步移交队列一起使用，会导致 栈溢出 StackOverflowError(单线程的情况下，随着最大线程数增多，稍有好转，说白了还是看任务能否处理得过来)
     * 实际上，只有当队列的线程池是无界（比如newCachedThreadPool）的或者可以拒绝任务时，使用SynchronousQueue才有价值
     *
     * 根据线程栈信息，分析下造成栈溢出的原因
     * 由于在以下设定中，核心线程数已满，队列容量为空，且任务执行时长比较长导，在提交超过处理能力的任务时，多出的任务会直接触发拒绝策略
     * 而该示例中使用的拒绝策略为DiscardOldestPolicy，即抛弃任务队列中最老的任务，再将新任务添加进去：
     * ThreadPoolExecutor.execute添加任务——》ThreadPoolExecutor.reject 触发拒绝策略——》ThreadPoolExecutor$DiscardOldestPolicy.rejectedExecution 执行决绝策略，该拒绝策略核心源码为
     *          e.getQueue().poll();
     *          e.execute(r);
     * 由于 poll 和offer方法不同于put和take方法，并不会阻塞。poll移除并不起作用，因为同步队列没有容量。然后线程池会继续执行添加任务逻辑，不断的循环这个过程
     *
     */
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor exec = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 10; i++) {
            TimeUnit.MILLISECONDS.sleep(5);
            try{
                exec.execute(() -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                        System.out.println("提交的延时任务");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }catch (Error e){
                e.printStackTrace();
            }
        }
        exec.shutdown();
    }

}
