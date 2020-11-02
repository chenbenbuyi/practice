package thinckingInJava.part21_juc.p5;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/25 14:31
 * @Description
 */
public class ToastMatic {
    public static void main(String[] args) throws InterruptedException {
        TosatQueue dayQueue = new TosatQueue(),
                butteredQueue = new TosatQueue(),
                jamQueue = new TosatQueue();
        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.execute(new Toaster(dayQueue));
//        executorService.execute(new Butterer(dayQueue, butteredQueue));
//        executorService.execute(new Jammer(butteredQueue, jamQueue));
        executorService.execute(new Eater(jamQueue));
        TimeUnit.SECONDS.sleep(3);
        executorService.shutdownNow();
    }
}

class Toast {
    public enum Status {
        DAY, BUTTERED, JAMMED;
    }

    private Status status = Status.DAY;
    private final int id;

    public Toast(int id) {
        this.id = id;
    }

    public void butter() {
        status = Status.BUTTERED;
    }

    public void jam() {
        status = Status.JAMMED;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Toast{" +
                "status=" + status +
                ", id=" + id +
                '}';
    }
}

class TosatQueue extends LinkedBlockingQueue<Toast> {

}

class Toaster implements Runnable {
    private TosatQueue tosatQueue;
    private int count = 0;
    private Random rand = new Random(50);

    public Toaster(TosatQueue tosatQueue) {
        this.tosatQueue = tosatQueue;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
                Toast toast = new Toast(count++);
                System.out.println(toast);
                tosatQueue.put(toast);
            } catch (InterruptedException e) {
                System.out.println("Toaster 中断");
            }
        }
        System.out.println("Toaster 线程退出");
    }
}


class Butterer implements Runnable {
    private TosatQueue dayQueue, butteredQueue;

    public Butterer(TosatQueue dayQueue, TosatQueue butteredQueue) {
        this.dayQueue = dayQueue;
        this.butteredQueue = butteredQueue;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Toast toast = dayQueue.take();
                toast.butter();
                System.out.println(toast);
                butteredQueue.put(toast);
            } catch (InterruptedException e) {
                System.out.println("Butterer 中断");
            }
        }
        System.out.println("Butterer 线程退出");
    }
}

class Jammer implements Runnable {
    private TosatQueue butteredQueue, jammerQueue;

    public Jammer(TosatQueue butteredQueue, TosatQueue jammerQueue) {
        this.butteredQueue = butteredQueue;
        this.jammerQueue = jammerQueue;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Toast toast = butteredQueue.take();
                toast.jam();
                System.out.println(toast);
                jammerQueue.put(toast);
            } catch (InterruptedException e) {
                System.out.println("Jammer 中断");
            }
        }
        System.out.println("Jammer 线程退出");
    }
}

class Eater implements Runnable {
    private int count = 0;
    private TosatQueue jammerQueue;

    public Eater(TosatQueue jammerQueue) {
        this.jammerQueue = jammerQueue;
    }

    @Override
    public void run() {
        /**
         * 理解：Thread.interrupt()方法不会中断一个正在运行的线程。这一方法实际上完成的是，
         * 设置线程的中断标示位，在线程受到阻塞的地方（如调用sleep、wait、join等地方）抛出一个异常InterruptedException，并且中断状态也将被清除，这样线程就得以退出阻塞的状态。\
         *
         * 注意：这里线程检测到中断状态后并不会停止运行while循环，因为这里的try-catch 语句在while循环体内，
         *  当线程池中断线程发生中断异常后，异常被捕获的同时会将中断信号复原，也就导致Thread.interrupted()总是为true
         *  InterruptedException异常抛出后会立即将线程的中断标示位清除，即抛出异常是为了线程从阻塞状态醒过来，并在结束线程前让程序员有足够的时间来处理中断请求。
         *  循环将一直持续下去。两种方案可以退出线程方法：https://www.cnblogs.com/onlywujun/p/3565082.html
         *      一，将while至于try-catch块内，通过异常机制终止线程运行
         *      二，在捕获的异常中继续调用 Thread.currentThread().interrupt(); 让线程中断状态得以保持
         */
        while (!Thread.interrupted()) {
            try {
                Toast toast = jammerQueue.take();
                if (toast.getId() != count++ || toast.getStatus() != Toast.Status.JAMMED) {
                    System.out.println("发生错误：" + toast);
                    System.exit(1);
                } else {
                    System.out.println("卡拉卡拉：" + toast);
                }
                System.out.println("现在进入阻塞。。。");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("线程运行中。，。，，");
            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
                System.out.println("Eater收到中断");
            }
        }
        System.out.println("Eater 线程退出");
    }
}
