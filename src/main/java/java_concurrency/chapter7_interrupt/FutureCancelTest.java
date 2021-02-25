package java_concurrency.chapter7_interrupt;

import java.util.concurrent.*;

/**
 * @author chen
 * @date 2021/2/20 15:56
 * @Description 任务取消 测试
 */
public class FutureCancelTest {
    static ThreadPoolExecutor exec = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    /**
     * Future cancel(boolean mayInterruptIfRunning) 的方法注释有点绕，但也请仔细阅读并理解：
     * 方法返回值情况：
     * false :   如果任务已经完成或者已经取消(已经调用过一次cancel方法)或者因为其它原因无法取消，则该方法将返回false
     * true ：   除返回false之外的其它任何情况。在方法返回 true 的情况，如果任务尚未启动，则任务永远不会启动；
     * 方法参数(mayInterruptIfRunning)决定了在任务执行过程中，要不要通过中断其执行线程来尝试打断任务.
     * true : 如果任务已经启动,则会中断任务线程执行
     * false: 允许正在执行的任务完成
     * 执行取消的现实意义在于，如果任务已被标识为已取消，那么获取其结果的线程，就不需要等待其完成，会直接抛出已取消异常，即使任务事实上还在执行。
     * <p>
     * 关联：执行该方法后isDone方法总是返回true,如果该方法返回true,isCancelled也总是返回true
     */
    public static void main(String[] args) {
        Future<?> result = exec.submit(new IncrementSequence());
        try {
            TimeUnit.SECONDS.sleep(1);
//            result.cancel(true);  // 在获取结果之前执行取消操作，获取结果的操作将抛出运行时异常：CancellationException（该异常表示由于任务被取消，无法检索值生成任务的结果）
            boolean cancel = result.cancel(false);
            System.out.println("第一次取消时的返回结果："+cancel);
            System.out.println("第二次取消时的返回结果："+result.cancel(true));  // 测试你会发现，第二次的取消的传入中断参数已经不起作用了，因为任务已经标识为取消了
            result.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("任务执行超时，取消任务！");
            result.cancel(false);
        }
    }
}


class IncrementSequence implements Runnable {
    private int sequence = 0;

    @Override
    public void run() {
        try {
            while (true) {
                sequence++;
                System.out.println("sequence = " + sequence);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("中断异常");
        }

    }
}