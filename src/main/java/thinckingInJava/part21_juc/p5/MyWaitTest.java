package thinckingInJava.part21_juc.p5;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/13 17:32
 */
public class MyWaitTest {
    public static void main(String[] args) {
        MyWait myWait = new MyWait();
        Thread thread = new Thread(myWait::fun1);
        thread.start();
        Thread thread2 = new Thread(myWait::fun2);
        thread2.start();
        /**
         * 主线程调用通知方法会抛出IllegalMonitorStateException 异常，主线程并不是MyWait对象锁的拥有者
         * 实际上，只能在同步方法或同步代码块中调用 wait.notify和notifyAll方法
         */
//        myWait.notify();
    }
}


class MyWait {

    public synchronized void fun1() {
        System.out.println("同步方法fun1执行");
        try {
            System.out.println("同步方法fun1开始等待");
            super.wait(3);
            System.out.println("同步方法fun1等待结束");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("同步方法fun1执行完成");
    }


    public synchronized  void fun2() {
        System.out.println("同步方法fun2执行");
    }
}