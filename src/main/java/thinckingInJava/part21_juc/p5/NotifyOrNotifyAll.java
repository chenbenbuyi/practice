package thinckingInJava.part21_juc.p5;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/16 7:40
 * @Description 关于notifyAll 是否会唤醒所有任务的测试
 */
public class NotifyOrNotifyAll {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 8, 20, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
            executor.execute(new Task());
        }
        executor.execute(new Task2());
        Timer timer = new Timer();
        // 在指定的延迟之后 开始 ，重新执行 固定速率的指定任务。
        timer.scheduleAtFixedRate(new TimerTask() {
            boolean prod = true;
            @Override
            public void run() {
                // 交替执行对挂起任务的唤醒
                if (prod) {
                    System.out.println("notify() ");
                    Task.bloker.prod();
                    prod = false;
                } else {
                    System.out.println("notifyAll() ");
                    Task.bloker.prodAll();
                    prod = true;
                }
            }
        }, 400, 400);
        TimeUnit.SECONDS.sleep(5);
        //定时器取消
        System.out.println("休息了5 后定时器取消");
        timer.cancel();
        // 任务2唤醒
        System.out.println("任务2开始唤醒");
        Task2.bloker.prodAll();
        executor.shutdownNow();
    }
}

class Bloker {
    synchronized void waitingCall() {
        while (!Thread.interrupted()) {
            try {
                System.out.println("线程任务 " + Thread.currentThread()+" 开始挂起等待");
                wait();
                System.out.println("线程任务 " + Thread.currentThread()+" 被唤醒。。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void prod() {
        notify();
    }

    synchronized void prodAll() {
        notifyAll();
    }
}


class Task implements Runnable {
    static Bloker bloker = new Bloker();

    @Override
    public void run() {
        bloker.waitingCall();
    }
}

class Task2 implements Runnable {
    static Bloker bloker = new Bloker();

    @Override
    public void run() {
        bloker.waitingCall();
    }
}
