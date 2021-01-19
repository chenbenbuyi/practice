package java_concurrency.chapter5;

import thinckingInJava.part21_juc.p7.BoundedHashSet;

import java.util.concurrent.Semaphore;

/**
 * @author chen
 * @date 2021/1/19 22:55
 * @Description 信号量
 * {@link BoundedHashSet}
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i <10 ; i++) {
            new Thread(() -> {
                try {
                    // 获取不到许可将阻塞
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"线程获得许可");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        /**
         * 在初始化许可数量之外继续释放许可
         *  由此可证，许可类似于生产出来的，可以比初始化许可多
         */
        semaphore.release();
        semaphore.release();
        semaphore.release();
    }
}
