package thinckingInJava.part21_juc.p2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/3/30 21:50
 * @Description 线程池
 */
public class TheadPool {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue(5), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i <5 ; i++) {
            executorService.execute(new LiftOff(10));
        }
        executorService.shutdown();
    }
}
