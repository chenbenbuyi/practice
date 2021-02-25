package java_concurrency.chapter7_interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/1/28 21:52
 * @Description 线程中断测试
 */
public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    private static void test1() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "的中断状态：" + Thread.currentThread().isInterrupted());
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                // 中断异常抛出后，该线程的中断状态将被清除
                System.out.println(Thread.currentThread().getName() + "异常之后中断状态：" + Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt(); // 恢复中断状态
                System.out.println(Thread.currentThread().getName() + "异常之后中断状态：" + Thread.currentThread().isInterrupted());
            }
        }, "测试线程");
        thread1.start();
        TimeUnit.SECONDS.sleep(1);
        // 执行中断操作
        thread1.interrupt();
    }

    private static void test2() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName()+"中断状态已被设置");
                    /**
                     * 静态方法 interrupted 会清除当前线程的中断状态 如果连续两次调用该方法，第二次会返回false
                     *  实际上，Thread 对象内部，interrupted和isInterrupted底层调用的是同一个方法，只是传入了是或否清除线程中断状态的布尔值
                     */
                    boolean interrupted = Thread.interrupted();
                    System.out.println(Thread.currentThread().getName() +"线程是否中断："+interrupted);
                    System.out.println(Thread.currentThread().getName()+"interrupted方法调用之后中断状态："+Thread.currentThread().isInterrupted());
                }
            }
        }, "测试线程");
        thread1.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(thread1.getName() + "的中断状态：" + thread1.isInterrupted());
        // 执行中断操作
        thread1.interrupt();
        System.out.println(thread1.getName() + "的中断状态2：" + thread1.isInterrupted());
    }

}
