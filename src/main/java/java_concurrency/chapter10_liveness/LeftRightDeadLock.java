package java_concurrency.chapter10_liveness;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/2/28 19:53
 * @Description 顺序性死锁——多个线程视图以不同的顺序来获取相同的锁
 *  这种死锁很好解决，就是让多个线程按顺序获取锁
 */
public class LeftRightDeadLock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() throws InterruptedException {
        synchronized (left) {
            TimeUnit.MILLISECONDS.sleep(60);
            synchronized (right) {
                System.out.println("先左后右加锁");
            }
        }
    }

    public void rightLeft() throws InterruptedException {
        synchronized (right) {
            TimeUnit.MILLISECONDS.sleep(100);
            synchronized (left) {
                System.out.println("先右后左加锁");
            }
        }
    }

    public static void main(String[] args) {
        LeftRightDeadLock leftRightDeadLock = new LeftRightDeadLock();
        new Thread(() -> {
            try {
                leftRightDeadLock.leftRight();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                leftRightDeadLock.rightLeft();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


}
