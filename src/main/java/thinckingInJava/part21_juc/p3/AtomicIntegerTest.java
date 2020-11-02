package thinckingInJava.part21_juc.p3;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chen
 * @date 2020/5/7 7:33
 * @Description 原子类
 */
public class AtomicIntegerTest implements Runnable {

    private AtomicInteger i = new AtomicInteger(0);

    private int getValue() {
        return i.get();
    }

    private synchronized void eventIncrement() {
        i.getAndAdd(2); // 增2
    }

    @Override
    public void run() {
        while (true) {
            eventIncrement();
        }
    }


    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("5 s 后退出");
                System.exit(0);
            }
        }, 5000);


        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 10, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        AtomicIntegerTest at = new AtomicIntegerTest();
        executor.execute(at);
        while (true) {
            int value = at.getValue();
            if (value % 2 != 0) {
                System.out.println("val=" + value);
                System.exit(0);
            }
        }
    }
}
