package thinckingInJava.part21_juc.p3;

import thinckingInJava.part21_juc.p2.TheadFactoryTest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/4/27 7:31
 * @Description 某个任务对一个标记为 Synchronized 方法的调用，在该调用方法返回之前，其它任务要调用该类中的其它同步方法，必须扥该方法结束释放锁之后才行
 */
public class SynchronizedTest {

    public static synchronized void fun(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Synchronized 方法 fun");
    }


    public static synchronized void fun2(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Synchronized 方法 fun2");
    }


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 7, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), new TheadFactoryTest(), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                fun();
                System.out.println("我在此停留");
                fun2();
            });
        }
        executor.shutdown();
    }


}
