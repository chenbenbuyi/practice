package thinckingInJava.part21_juc.p4;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/5 8:45
 * @Description 在线程上地调用 interrupt 时，只有在线程阻塞或者要进入阻塞操作才会产生中断（IO或synchronized甚至不会被中断）
 * so,如何在非阻塞调用的情况下也能退出线程呢？
 */
public class Interrupting3 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Blocked3());
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        // 中断当前线程并抛出异常
        thread.interrupt();
    }

}

class NeedCleanup {
    private final int id;

    public NeedCleanup(int ident) {
        this.id = ident;
        System.out.println("NeedCleanup: " + id);
    }

    public void cleanup() {
        System.out.println("cleanup id: " + id);
    }
}

class Blocked3 implements Runnable {

    private volatile double d = 0.0;

    @Override
    public void run() {
        try {
            // interrupted 检查线程中断状态 ，同时该方法会清除中断状态
            while (!Thread.interrupted()) {
                NeedCleanup needCleanup = new NeedCleanup(1);
                try {
                    System.out.println("休眠 1 秒中。。。");
                    TimeUnit.SECONDS.sleep(1);
                    NeedCleanup needCleanup2 = new NeedCleanup(2);
                    try {
                        System.out.println("执行一个耗时的但非阻塞操作");
                        for (int i = 0; i < 2500000; i++) {
                            while (true){
                                d = (d + Math.PI + Math.E) / d;
                            }
                        }
                        System.out.println("耗时操作完成");
                    } finally {
                        needCleanup2.cleanup();
                    }
                } finally {
                    needCleanup.cleanup();
                }
            }
            System.out.println("退出 while 循环");
        } catch (InterruptedException e) {
            System.out.println("通过中断退出。。。");
        }
    }
}



