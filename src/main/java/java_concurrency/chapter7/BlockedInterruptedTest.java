package java_concurrency.chapter7;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/2/18 20:09
 * @Description 可中断阻塞方法调用
 */
public class BlockedInterruptedTest {

    public static void main(String[] args) throws InterruptedException {
        MyThread thread = new MyThread();
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("线程中断状态：" + thread.isInterrupted());
    }


    /**
     * 对于一些不支持取消但调用了可中断的阻塞方法的操作，如果在循环中调用这些阻塞方法，在阻塞方法响应中断时，不能过早的恢复中断状态。
     * 如下 代码1 处，在循环条件下，如果在异常捕获之后恢复中断，会引起死循环，因为大多数可中断的阻塞方法都会在入口处或者重要工作时检测中断状态，以便更快的响应中断，如果检测到线程已被设置为中断会直接抛出中断异常
     * 正确的做法是在本地保存中断状态，在方法最终返回前才执行状态恢复的代码。
     */
    static class MyThread extends Thread {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

        @Override
        public void run() {
//            while (true) {
//                try {
//                    queue.take();
//                } catch (InterruptedException e) {
//                    System.out.println("检测到中断，抛出中断异常");
//                    // 这里可以重新尝试
//                    Thread.currentThread().interrupt();  // 代码1
//                }
//            }


            boolean interrupted = false;
            try {
                while (true) {
                    try {
                        queue.take();
                    } catch (InterruptedException e) {
                        System.out.println("检测到中断，抛出中断异常");
                        // 这里可以重新进行业务尝试
                        interrupted = true;
                    }
                }
            } finally {
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }


}
