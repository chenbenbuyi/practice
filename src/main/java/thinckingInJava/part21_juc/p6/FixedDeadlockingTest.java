package thinckingInJava.part21_juc.p6;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/25 23:48
 * @Description
 */
public class FixedDeadlockingTest {
    public static void main(String[] args) throws InterruptedException, IOException {
        int pander = 5;
        if (args.length > 0) {
            pander = Integer.parseInt(args[0]);
        }
        int size = 5;
        if (args.length > 1) {
            size = Integer.parseInt(args[1]);
        }
        ExecutorService executorService = Executors.newCachedThreadPool();
        Chopstick[] chopsticks = new Chopstick[size];
        for (int i = 0; i < size; i++) {
            chopsticks[i] = new Chopstick();
        }
        for (int i = 0; i < size; i++) {
            if (i < (size - 1)) {
                executorService.execute(new Philosopher(chopsticks[i], chopsticks[(i + 1) % size], i, pander));
            }else {
                // 和死锁例子的区别在于，该行代码确保了循环依赖最后一个先拿起和放下左边的筷子
                executorService.execute(new Philosopher(chopsticks[0], chopsticks[i], i, pander));
            }
        }
        if (args.length == 3 && args[2].equals("timeout")) {
            TimeUnit.SECONDS.sleep(5);
        } else {
            System.out.println("回车键结束");
            System.in.read();
        }
        executorService.shutdownNow();
    }
}
