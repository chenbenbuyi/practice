package thinckingInJava.part21_juc.p2;

import java.util.concurrent.*;

/**
 * @author chen
 * @date 2020/3/30 22:21
 * @Description
 */
class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(3);
        return "返回的任务ID:" + id;
    }


}


public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = new ThreadPoolExecutor(0, 10,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>());

        for (int i = 0; i < 5; i++) {
            Future<String> result = executorService.submit(new TaskWithResult(i));
//            System.out.println(result.get());
            // get 有一个重载的方法，可以指定超时时间
            System.out.println(result.get(2,TimeUnit.SECONDS));
        }

        executorService.shutdown();
    }
}