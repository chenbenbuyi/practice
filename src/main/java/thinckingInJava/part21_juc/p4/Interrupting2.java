package thinckingInJava.part21_juc.p4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chen
 * @date 2020/7/2 22:35
 * @Description 测试显示锁的中断
 */
class LockBlocked {

    private Lock lock = new ReentrantLock();

    public LockBlocked() {
        /**
         *  通过构造器显示的获取对象锁
         *  思考：lock.lock 到底获取的是不是对象锁？可通过 synchronized 关键字特性，尝试在另外的线程中调用同步方法
         */
        lock.lock();
    }

    public  void f() {
        try {
            /**
             * 通过分析源码可以知道lock方法默认处理了中断请求，一旦监测到中断状态，则中断当前线程；
             * 而lockInterruptibly()则直接抛出中断异常，由上层调用者区去处理中断
             */
            System.out.println("通过 lockInterruptibly 方法获取锁");
            lock.lockInterruptibly();
            System.out.println("通过 lockInterruptibly 方法成功获取到锁");
        } catch (InterruptedException e) {
            System.out.println("通过 lockInterruptibly 方法获取锁的线程被中断。。。。");
        }
        System.out.println("f 方法任务结束。。。。");
    }


    public void g() {
        /**
         * 下面直接执行lock.unlock()执行源码会判断当前线程有没有获取到锁，如果没获取到锁就解锁会抛出 new IllegalMonitorStateException();
         * 因为当前线程加的锁只能在当前线程中进行解锁，是无法在其他线程中进行解锁的，因为其它线程并不持有该锁。
         */
//        lock.unlock();
        /**
         * lock方法会忽略中断请求，继续获取锁直到成功；而lockInterruptibly则直接抛出中断异常来立即响应中断，由上层调用者处理中断。
         */
        System.out.println("通过 lock 方法获取锁");
        lock.lock();
        System.out.println("通过 lock 方法成功获取到锁，g 方法任务结束");
    }

    public void h() {
        System.out.println("通过 tryLock 方法获取锁");
//        lock.tryLock();
        try {
            lock.tryLock(5,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("通过 tryLock 方法成功获取到锁，h 方法任务结束");
    }
}


class Blocked2 implements Runnable {

    LockBlocked lockBlocked = new LockBlocked();

    @Override
    public void run() {
        System.out.println("当前线程 " + Thread.currentThread().getName() + " 准备获取锁");
        lockBlocked.h();
    }
}

public class Interrupting2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Blocked2());
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("发送线程中断通知给线程: " + thread.getName());
        thread.interrupt();
    }
}



