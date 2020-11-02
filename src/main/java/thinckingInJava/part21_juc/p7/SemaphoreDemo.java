package thinckingInJava.part21_juc.p7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author chen
 * @date 2020/8/13 21:20
 * @Description
 */

public class SemaphoreDemo {
    private final static int SIZE =1;

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        final Pool<Fat> pool = new Pool<>(Fat.class, SIZE);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < SIZE; i++) {
            // 执行的线程任务为： 取出对象，一秒延迟后再放回
            executor.execute(new CheckoutTask<>(pool));
        }
//        TimeUnit.NANOSECONDS.sleep(1000);
        List<Fat> list = new ArrayList<>();
        for (int i = 0; i <SIZE ; i++) {
            Fat fat = pool.checkOut();
            // 先检出对象执行，这里比线程池任务先执行，因为主线程是直接执行的，没有启动单独线程的额外时间开销
            fat.operation();
            list.add(fat);
        }

        Future<?> future = executor.submit(() -> {
            try {
                System.out.println("我在这里呀");
                pool.checkOut();
                System.out.println("我在这里呀2");
            } catch (InterruptedException e) {
                System.out.println("checkOut 被中断");
            }
        });
        //  对于提交的 Runnable类型，get方法将在成功完成时返回null。
//        System.out.println("submit 返回值："+future.get());
        TimeUnit.SECONDS.sleep(2);
        // 打断阻塞
        future.cancel(true);
        for (Fat fat : list) {
            System.out.println("签入操作");
            pool.checkIn(fat);
        }
        // 第二次签入将被忽略
        for (Fat fat : list) {
            pool.checkIn(fat);
        }
        executor.shutdown();
    }
}

class Fat {
    private volatile double d;
    private static int counter = 0;
    private final int id = counter++;

    // 耗时的对象构造操作
    public Fat() {
        for (int i = 0; i < 10000; i++) {
            d += (Math.PI + Math.E) / (double) i;
        }
    }

    public void operation() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Fat{" +
                "d=" + d +
                ", id=" + id +
                '}';
    }
}

class CheckoutTask<T> implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private Pool<T> pool;

    public CheckoutTask(Pool<T> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            System.out.println("当前线程 "+Thread.currentThread().getName()+" 执行操作对象签出");
            T t = pool.checkOut();
            System.out.println(this + " check out " + t);
            TimeUnit.SECONDS.sleep(1);
            System.out.println(this + " check int " + t);
            pool.checkIn(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "CheckoutTask{" +
                "id=" + id +
                '}';
    }
}
