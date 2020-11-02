package thinckingInJava.part21_juc.p4;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/6/11 22:14
 * @Description
 */
class SleepBlocked implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("退出休眠阻塞线程 SleepBlocked");
    }
}

class IOBlocked implements Runnable {

    private InputStream in;

    public IOBlocked(InputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        System.out.println("等待输入。。。");
        try {
            in.read();
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("IO阻塞线程 IOBlocked 被中断");
            } else {
                throw new RuntimeException(e);
            }
        }
        System.out.println("退出IO阻塞线程 IOBlocked ");
    }
}


class SynchronizedBlocked implements Runnable {

    public synchronized void f() {
        while (true) {
            Thread.yield();
        }
    }

    // 创建对象的时候，获取同步阻塞对象的锁，一直持有不释放
    public SynchronizedBlocked() {
        new Thread(() -> f()).start();
    }

    //当前线程试图获取锁
    @Override
    public void run() {
        System.out.println("尝试获取SynchronizedBlocked对象锁");
        f();
        System.out.println("成功获取到SynchronizedBlocked对象锁，退出线程方法");
    }
}


public class Intterupting {
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 100, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());

    static void test(Runnable r) throws InterruptedException, IOException {
        Future<?> f = executor.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("中断线程 " + r.getClass().getName());
        f.cancel(true);
        System.out.println("已发送中断信号给线程" + r.getClass().getName());
        executor.shutdown();
        System.in.close();
    }


    /**
     * 通过输出表明，对于sleep调用，线程可以被中断并抛出异常，但是对于IO阻塞或同步阻塞，都不能中断执行，线程依旧是阻塞状态
     * 一种略显笨拙的解决方式是关闭任务在其上发生阻塞的底层资源
     * 但是，测试证明，书本上示例中 System.in这种输入流关闭也无法中断线程，但是socket输入流可以
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException, IOException {
//        test(new SleepBlocked());
        test(new IOBlocked(System.in));
//        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
//        System.exit(0);

    }

}