package java_concurrency.chapter7_interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/2/18 19:42
 * @Description 对于不会调用可中断阻塞操作的线程任务，可以通过在任务代码中轮询线程的中断状态来检测中断
 */
public class NonBlockedInterruptedTest {

    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    public static void test1() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("开启新的线程任务");
            while (true) {
                /**
                 * Thread.interrupted() 获取的当前线程其实就是 Thread.currentThread()
                 */
                if(Thread.interrupted()){
                    System.out.println("线程检测到中断。。。。。。。");
                    return;
                }
            }
        });
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                thread.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        thread.start();
        thread.join();
        System.out.println("主线程结束！");
    }

    public static void test2() throws InterruptedException {
        Thread thread = new Thread(new MyThread());
        thread.start();
        TimeUnit.SECONDS.sleep(3);
        thread.interrupt();
        System.out.println("主线程结束！");
    }


    private static class MyThread extends Thread {
        @Override
        public void run() {
            while (!interrupted()) {
                // 正常的任务逻辑
            }
            System.out.println("线程任务结束");
        }
    }

}
