package thinckingInJava.part21_juc.p3;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/6/3 22:35
 * @Description 线程本地存储 为使用同一个变量的每个线程创建本地线程自己的本地存储
 */

public class ThrealLocalVariableHolder {
    private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
        Random random = new Random(50);

        @Override
        protected Integer initialValue() {
            return random.nextInt(100);
        }
    };

    protected static void increment() {
        value.set(value.get() + 1);
    }

    public static Integer getValue() {
        return value.get();
    }


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(0, 5, 5, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Accessor(i));
        }
        TimeUnit.SECONDS.sleep(3);
        executorService.shutdown();
    }
}

class Accessor implements Runnable {

    private int id;

    public Accessor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            ThrealLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }

    @Override
    public String toString() {
        return "Accessor{" +
                "#" + id + " : " + ThrealLocalVariableHolder.getValue() +
                '}';
    }
}




