package thinckingInJava.part21_juc.p6;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/25 21:23
 * @Description 关于死锁的测试
 * 关于死锁的四个满足条件：
 *  ① 互斥条件，即任务使用的资源中至少有一个是不能共享的
 *  ② 至少有一个任务必须持有资源且正在等待获取一个当前被别的任务持有的资源
 *  ③ 资源不能被线程任务抢占
 *  ④ 必须有循环等待
 */
public class DeadlockingTest {
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
            executorService.execute(new Philosopher(chopsticks[i], chopsticks[(i + 1) % size], i, pander));
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
