package java_concurrency.chapter8_threadpool;

import java.util.concurrent.*;

/**
 * @author chen
 * @date 2021/2/26 20:50
 * @Description 测试 线程饥饿死锁 的发生
 */
public class ThreadPoolDeallock {
    static ThreadPoolExecutor exec = new ThreadPoolExecutor(1, 2,
            1L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<String> callable1 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                return "线程饥饿死锁";
            }
        };

        Callable<String> callable2 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                try{
                    Future<String> future = exec.submit(callable1);
                    System.out.println("等待callable1的任务结果：" + future.get());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return "线程饥饿死锁";
            }
        };
        Future<String> future = exec.submit(callable2);
        /**
         * shutdown 方法并不会等待之前提交的任务执行完成，不会阻塞主线程。但此处的测试显示，shutdown之后,主线程直接退出了，除非 future.get() 获取子线程任务结果阻塞主线程
         * 但实际上，shutdown之后，由于子线程还在运行，虚拟机是并不会立即退出的。
         * 唯一的解释是代码本身的问题，导致虚拟机直接退出了执行
         * 添加在子线程任务中 try-catch 之后，果然发现是因为触发了拒绝策略导致的运行时异常，子线程任务直接就结束了，接着主线程也就完了。
         * 但是很奇怪，这里自己的队列明显是个无界队列，怎么会出发拒绝策略？
         * 其实，还是因为自己调用了shutdown，线程池已经无法继续接受新任务，自此，豁然开朗
         */
//        System.out.println("callable2任务的执行结果：" + future.get());
        exec.shutdown(); // shutdown 调用之后，线程池并不会立即处于terminated状态
        System.out.println(exec.isTerminated());

    }
}
