package thinckingInJava.part21_juc.extra;

import java_concurrency.chapter3.VolatileTest1;
import java_concurrency.chapter3.VolatileTest2;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chen
 * @date 2020/12/24 22:01
 * @Description 模拟在多线程环境下，测试 volatile 变量产生的线程安全问题
 * volatile 变量能保证线程间可见，但是变量自身的自增或自减操作无法保证原子性,除非用原子变量如AtomicInteger进行替代
 * {@link VolatileTest1}
 * {@link VolatileTest2}
 */
@Slf4j
public class VolatileTest {
    //    private static  int i = 0;
    private static volatile int i = 0;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final CountDownLatch countDownLatch = new CountDownLatch(20);
        for (int j = 0; j < 20; j++) {
            executorService.execute(() -> {
                for (int k = 0; k < 10000; k++) {
                    i++;
                    atomicInteger.incrementAndGet();
                }
                countDownLatch.countDown();
            });
        }
        executorService.shutdown();
        log.info("20个并发线程执行完成之后变量 i 的值：{},原子变量atomicInteger的值：{}", i,atomicInteger);
    }
}
