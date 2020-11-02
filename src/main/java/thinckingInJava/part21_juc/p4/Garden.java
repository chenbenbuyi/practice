package thinckingInJava.part21_juc.p4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/6/4 22:23
 * @Description 模拟从多个花园门入口进入花园的总人数统计和每个入口的人数统计
 */
class Garden {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 10, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 1; i < 6; i++) {
            executor.execute(new Entrance(i));
        }
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        executor.shutdown();
        if (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) {  // awaitTermination 如果所有任务都在给定的超时时间后结束，则返回true
            System.out.println("一些任务没有完全结束");
        }
        System.out.println("入园总人数：" + Entrance.getTotolCount());
        System.out.println("各个门口的总数统计：" + Entrance.sumEntrances());
    }


}


/**
 * 模拟花园总人数
 */
class Count {

    private int count = 0;

    private Random rand = new Random();

    public synchronized int increment() {
        int temp = count;
        if (rand.nextBoolean())
            Thread.yield();
        return count = ++temp;
    }

    public synchronized int value() {
        return count;
    }
}

/**
 * 模拟单个公园入口
 */
class Entrance implements Runnable {

    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<>();
    private int number = 0;
    private final int id;
    private static volatile boolean canceled = false;

    public static void cancel() {
        canceled = true;
    }

    public Entrance(int id) {
        this.id = id;
        entrances.add(this);
    }

    @Override
    public void run() {
        while (!canceled) {
            synchronized (this) {
                ++number;
            }
            System.out.println(this + " 总数：" + count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("线程被唤醒");
            }
        }
        System.out.println("停止：" + this);
    }

    @Override
    public String toString() {
        return id + "# 入口入园人数：" + getNumber();
    }

    public int getNumber() {
        return number;
    }

    public static int getTotolCount() {
        return count.value();
    }

    public static int sumEntrances() {
        int sum = 0;
        for (Entrance entrance : entrances) {
            sum += entrance.getNumber();
        }

        return sum;
    }
}
