package thinckingInJava.part21_juc.p5;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/13 23:46
 * @Description
 */
public class WaxOMatic {
    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8, 20, TimeUnit.SECONDS, new SynchronousQueue<>());
        executor.execute(new WaxOff(car));
        executor.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(2);
        executor.shutdownNow();
    }
}


class Car {
    private boolean waxOn = false;

    public synchronized void waxed() {
        waxOn = true;
        notifyAll();
    }

    public synchronized void buffed() {
        waxOn = false;
        notifyAll();
    }

    public synchronized void waitForWaxing() throws InterruptedException {
        while (!waxOn) {
            wait();
        }
    }

    public synchronized void waitForBuffing() throws InterruptedException {
        while (waxOn) {
            // 如果没有挂起，那么线程持有的锁不会释放
            wait();
        }
    }
}

class WaxOn implements Runnable {

    private Car car;

    public WaxOn(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println("开始打蜡");
                TimeUnit.MILLISECONDS.sleep(100);
                car.waxed();
                car.waitForBuffing();
            }
        } catch (InterruptedException e) {
            System.out.println("打蜡线程中断");
        }
        System.out.println("结束打蜡任务");
    }
}

class WaxOff implements Runnable {

    private Car car;

    public WaxOff(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                car.waitForWaxing();
                System.out.println("开始抛光");
                TimeUnit.MILLISECONDS.sleep(100);
                car.buffed();
            }
        } catch (InterruptedException e) {
            System.out.println("抛光线程中断");
        }
        System.out.println("结束抛光任务");
    }
}
