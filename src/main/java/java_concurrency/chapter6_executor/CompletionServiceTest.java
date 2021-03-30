package java_concurrency.chapter6_executor;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author chen
 * @date 2021/1/28 9:00
 * 当向Executor提交多个任务并且希望获得它们在完成之后的结果，如果用FutureTask的实现方式，需要循环获取每个与任务管理的Future,并调用get方法去获取任务执行结果；
 * 但是如果任务还未完成，获取结果的线程将阻塞直到任务完成或异常退出。由于不知道哪个任务优先执行完毕，使用这种方式非常繁琐且效率不会高。
 * 在jdk5时候提出接口CompletionService，它整合了Executor和BlockingQueue的功能，通过提交执行任务，通过类似队列操作的take和poll功能获取已经完成的任务，由此可以更加方便在多个任务执行时获取到任务执行结果
 */
public class CompletionServiceTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.SECONDS, new SynchronousQueue<>());
        CompletionService<String> completionService = new ExecutorCompletionService<>(executor);
        Random random = new Random(100);
        for (int i = 0; i < 10; i++) {
            Callable<String> callable = () -> {
                int waitTime = random.nextInt(20);
                TimeUnit.SECONDS.sleep(waitTime);
                return "在" + waitTime + "秒后返回的任务结果";
            };
            completionService.submit(callable);
        }
        /**
         *   take 获取并移除表示下一个已完成任务的 Future，如果目前不存在这样的任务，则等待。
         *   预测输出：根据任在务休眠时间由短到长依次输出，最短时间的执行任务返回之前，take会等待
         */
        for (int i = 0; i < 10; i++) {
            Future<String> take = completionService.take();
            System.out.println(take.get());
        }
        executor.shutdown();
    }
}
