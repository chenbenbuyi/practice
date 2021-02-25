package java_concurrency.chapter6_executor;

import java.util.concurrent.*;

/**
 * @author chen
 * @date 2021/1/27 21:47
 * @Description
 */
public class ExecutorTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(10);
                return "测试延迟任务返回值获取";
            }
        };
        /**
         *  测试突然发现调用超时接口的问题：
         *  利用线程池对象提交任务的时候，以下代码测试超时符合预期
         *  但是利用 FutureTask的时候，却发现其run方法本身就阻塞了，导致后面获取任务返回值的超时接口没法超时
         *   跟踪源码，发现task.run方法最终是调用了Callable接口中的call执行等待子任务完成。
         *      所以，task.run和thread.start开启线程异步执行并不一样，实际上这是个同步方法
         */
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new SynchronousQueue<>());
//        Future<String> future = executor.submit(callable);
//        future.get(2,TimeUnit.SECONDS);
//        executor.shutdown();
        FutureTask<String> task = new FutureTask<>(callable);
//        task.run();// 这是个同步阻塞的方法，实际执行的就是子任务中的代码
        new ExecutorTask.AsynTask().execute(task);
        /**
         *   思考：符合预期的超时并抛出异常了，为什么虚拟机没有停下来呢？
         *   因为超时是从获取值的角度考量的，但在虚拟机看来，还有子任务还在执行，虚拟机还不能退出
         */
        task.get(2, TimeUnit.SECONDS);
    }
}
