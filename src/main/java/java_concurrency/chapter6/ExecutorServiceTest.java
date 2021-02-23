package java_concurrency.chapter6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author chen
 * @date 2021/1/28 9:37
 * @Description 测试任务批量提交执行
 */
public class ExecutorServiceTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.SECONDS, new SynchronousQueue<>());
        Random random = new Random(100);
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Callable<String> callable = () -> {
                int waitTime = random.nextInt(20);
                TimeUnit.SECONDS.sleep(waitTime);
                return "在" + waitTime + "秒后返回的任务结果";
            };
            tasks.add(callable);
        }
        /**
         *  invokeAll 执行所有提交的任务集合，当所有任务都完成或调用线程中断或超时时，该方法将返回。如果超时后还有任务未执行，未完成的任务将被取消
         *  任务按照添加任务集合中迭代器的顺序返回。所以，这依赖于具体容器的迭代顺序
         */
        List<Future<String>> futures = executor.invokeAll(tasks);
//        List<Future<String>> futures = executor.invokeAll(tasks, 10, TimeUnit.SECONDS); // 待超时的需要认真对待超时的异常处理
        for (Future<String> future : futures) {
            System.out.println(future.get());
        }
        executor.shutdown();
        /**
         * shutdown() 关闭了提交通道，用submit()是无效的；而内部该怎么跑还是怎么跑，跑完再停。
         * shutdownNow() 能立即停止线程池，正在跑的和正在等待的任务都停下了。这样做立即生效，风险也比较大；
         * awaitTermination(long timeOut, TimeUnit unit) 会使当前线程阻塞，直到
         * 1.等所有已提交的任务（包括正在跑的和队列中等待的）执行完
         * 2.或者等超时时间到
         * 3.或者线程被中断，抛出InterruptedException
         *
         * shutdown()和 awaitTermination() 的区别
         * shutdown()后，不能再提交新的任务进去；但是awaitTermination()后，可以继续提交。
         * awaitTermination()是阻塞的，返回结果是线程池是否已停止（true/false）；shutdown()不阻塞。
         * awaitTermination 并不是用来关闭线程池，它只是用来检测 timeout 时间后线程池是否关闭。 一般在调用shutdown()方法后调用
         * 所以，awaitTermination 使用之前 必须先手动关闭线程池，否则一直会阻塞当前线程直到超时为止
         */
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}
