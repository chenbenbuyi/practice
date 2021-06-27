package thinckingInJava.part21_juc.p7;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/6/27 18:00
 * @Description 循环栅栏测试
 */
public class CyclicBarrierTest {

    static Random rand = new Random(47);
    public static void main(String[] args) {
        /**
         *  CyclicBarrier 带Runnable任务的构造器中，其任务由最后到达栅栏的线程执行
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> System.out.println(Thread.currentThread().getName() + "线程执行任务"));
        for (int i = 0; i <3 ; i++) {
            int time = rand.nextInt(5);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(time);
                        System.out.println(Thread.currentThread().getName()+"线程"+time+"秒后准备就绪");
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
