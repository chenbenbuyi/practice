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
         *  invokeAll 执行所有提交的任务集合，当所有任务都完成或调用线程中断或超时时，该方法将返回。如果超时后还有任务为执行，未完成的任务将被取消
         *  任务按照添加任务集合中迭代器的顺序返回。所以，这依赖于具体容器的迭代顺序
         */
        List<Future<String>> futures = executor.invokeAll(tasks);
//        List<Future<String>> futures = executor.invokeAll(tasks, 10, TimeUnit.SECONDS); // 待超时的需要认真对待超时的异常处理
        for (Future<String> future : futures) {
            System.out.println(future.get());
        }
        executor.shutdown();
    }
}
