package thinckingInJava.part21_juc.p3;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/5/7 7:33
 * @Description 原子性测试
 */
public class AtomicityTest implements Runnable {

//    private  int i = 0; 直接的类变量，在多线程的情况下，还会存在可视性问题
    private volatile int i = 0;

    //    private  int getValue() {  只同步一个方法，不会影响多线程并发的取值，这个时候，i值可能处于不稳定的中间状态而被读取
    private int getValue() {
        return i;
    }

    private synchronized void eventIncrement() {
        i++;
        i++;
    }

    @Override
    public void run() {
        while (true) {
            eventIncrement();
        }
    }


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 10, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        AtomicityTest at = new AtomicityTest();
        executor.execute(at);
        while (true) {
            int value = at.getValue();
            if (value % 2 != 0) {
                System.out.println("val=" + value);
                System.exit(0);
            }
        }
    }
}
