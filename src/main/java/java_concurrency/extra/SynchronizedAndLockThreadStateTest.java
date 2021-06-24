package java_concurrency.extra;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @date: 2021/4/20 14:25
 * @author: chen
 * @desc: 探究
 * 1、线程获取隐式锁(synchronized)和显示锁 Lock 状态的不同
 * 2、lock 这种显示的加锁会产生死锁吗？
 * 结论：
 * 1、线程获取隐式锁(synchronized)和显示锁 Lock 获取不到时状态是不同的，前者是 BLOCKED，后者是WAITING状态。所以，不要以为只有 BLOCKED 的状态才是等待锁， WAITING/TIMEWAITING 的状态仍然可能是等待锁的状态。具体查看线程dump时获取锁状态内的值。
 *   所以，线程状态在几个大的枚举值的基础上，还有针对不同场景的详细区分，如 WAITING (parking)，TIMED_WAITING (parking),TIMED_WAITING (sleeping)等
 * 2、如果一个线程获取 lock 后没有显示的释放，会导致另外的线程获取统一个锁时长久的等待WAITING (parking)，与死锁无异。
 */
public class SynchronizedAndLockThreadStateTest {
    private static Object obj = new Object();
    private static Lock lock = new ReentrantLock();
    private static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            synchronized (obj) {
                System.out.println("线程1获取到对对象锁");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"线程1");
        thread1.start();
        Thread thread2 = new Thread(() -> {
            synchronized (obj) {
                System.out.println("线程2任务");
            }

        },"线程2");
        thread2.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("隐式锁时线程1线程状态：" + thread1.getState());
        System.out.println("隐式锁时线程2线程状态：" + thread2.getState());

        /**
         *  关于显示的锁中的方法，有以下说明：
         *  假如线程A和线程B使用同一个锁Lock，此时线程A通过 Lock.lock()首先获取到锁，并且始终持有不释放。如果此时B要去获取锁，有四种方式：
         *  Lock.lock(): 此方式会始终处于等待中，即使调用B.interrupt()也不能中断，除非线程A调用LOCK.unlock()释放锁。
         *  Lock.lockInterruptibly(): 此方式会等待，但当调用B.interrupt()会被中断等待，并抛出InterruptedException异常，否则会与lock()一样始终处于等待中，直到线程A释放锁。
         *  Lock.tryLock(): 获取不到锁并直接返回false，去执行下面的逻辑，不会等待获取锁
         *  Lock.tryLock(10, TimeUnit.SECONDS)：该处会在10秒时间内处于等待中，但当调用B.interrupt()会被中断等待，并抛出InterruptedException。10秒时间内如果线程A释放锁，会获取到锁并返回true，否则10秒过后会获取不到锁并返回false，去执行下面的逻辑。
         *
         */

        Thread thread3 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程3获取到显示锁");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }

        },"线程3");
        thread3.start();
        Thread thread4 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程4任务");
            } finally {
                lock.unlock();
            }

        },"线程4");
        thread4.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("显示锁Lock线程3线程状态：" + thread3.getState());
        System.out.println("显示锁Lock线程4线程状态：" + thread4.getState());




        Thread thread5 = new Thread(() -> {
            lock2.lock();
            try {
                System.out.println("线程3获取到显示锁");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
//                lock2.unlock();
            }

        },"线程5");
        thread5.start();
        Thread thread6 = new Thread(() -> {
            System.out.println("线程6任务准备加锁");
            // 由于 线程5没有释放锁，该加锁调用会始终处于等待中 java.lang.Thread.State: WAITING (parking)
            lock2.lock();
            System.out.println("线程6任务准备加锁成功");
            try {
                System.out.println("线程6任务");
            } finally {
                lock2.unlock();
            }

        },"线程6");
        thread6.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("显示锁Lock线程5线程状态：" + thread5.getState());
        System.out.println("显示锁Lock线程6线程状态：" + thread6.getState());
    }


    /**
     *  线程dump 分析参考 ：https://www.javatang.com/archives/2017/10/25/36441958.html
     *
     * 线程dump——
     *
     * "线程6" #16 prio=5 os_prio=0 tid=0x00000000194a7000 nid=0x2654 waiting on condition [0x000000001a21f000]
     *    java.lang.Thread.State: WAITING (parking)
     * 	at sun.misc.Unsafe.park(Native Method)
     * 	- parking to wait for  <0x00000000d6346d60> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
     * 	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
     * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
     * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireQueued(AbstractQueuedSynchronizer.java:870)
     * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(AbstractQueuedSynchronizer.java:1199)
     * 	at java.util.concurrent.locks.ReentrantLock$NonfairSync.lock(ReentrantLock.java:209)
     * 	at java.util.concurrent.locks.ReentrantLock.lock(ReentrantLock.java:285)
     * 	at java_concurrency.extra.SynchronizedAndLockThreadStateTest.lambda$main$5(SynchronizedAndLockThreadStateTest.java:102)
     * 	at java_concurrency.extra.SynchronizedAndLockThreadStateTest$$Lambda$6/1534030866.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     *
     * "线程5" #15 prio=5 os_prio=0 tid=0x00000000194a4000 nid=0x490 waiting on condition [0x000000001a11e000]
     *    java.lang.Thread.State: TIMED_WAITING (sleeping)
     * 	at java.lang.Thread.sleep(Native Method)
     * 	at java.lang.Thread.sleep(Thread.java:340)
     * 	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
     * 	at java_concurrency.extra.SynchronizedAndLockThreadStateTest.lambda$main$4(SynchronizedAndLockThreadStateTest.java:91)
     * 	at java_concurrency.extra.SynchronizedAndLockThreadStateTest$$Lambda$5/1389133897.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     *
     * "线程4" #14 prio=5 os_prio=0 tid=0x000000001a0c6000 nid=0xa6c waiting on condition [0x000000001ac2f000]
     *    java.lang.Thread.State: WAITING (parking)
     * 	at sun.misc.Unsafe.park(Native Method)
     * 	- parking to wait for  <0x00000000d6346a98> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
     * 	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
     * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
     * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireQueued(AbstractQueuedSynchronizer.java:870)
     * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(AbstractQueuedSynchronizer.java:1199)
     * 	at java.util.concurrent.locks.ReentrantLock$NonfairSync.lock(ReentrantLock.java:209)
     * 	at java.util.concurrent.locks.ReentrantLock.lock(ReentrantLock.java:285)
     * 	at java_concurrency.extra.SynchronizedAndLockThreadStateTest.lambda$main$3(SynchronizedAndLockThreadStateTest.java:69)
     * 	at java_concurrency.extra.SynchronizedAndLockThreadStateTest$$Lambda$4/885284298.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     *
     * "线程3" #13 prio=5 os_prio=0 tid=0x0000000019709800 nid=0x3524 waiting on condition [0x000000001ab2f000]
     *    java.lang.Thread.State: TIMED_WAITING (sleeping)
     * 	at java.lang.Thread.sleep(Native Method)
     * 	at java.lang.Thread.sleep(Thread.java:340)
     * 	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
     * 	at java_concurrency.extra.SynchronizedAndLockThreadStateTest.lambda$main$2(SynchronizedAndLockThreadStateTest.java:58)
     * 	at java_concurrency.extra.SynchronizedAndLockThreadStateTest$$Lambda$3/317574433.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     *
     * "线程2" #12 prio=5 os_prio=0 tid=0x000000001982b000 nid=0xd88 waiting for monitor entry [0x000000001aa2f000]
     *    java.lang.Thread.State: BLOCKED (on object monitor)
     * 	at java_concurrency.extra.SynchronizedAndLockThreadStateTest.lambda$main$1(SynchronizedAndLockThreadStateTest.java:34)
     * 	- waiting to lock <0x00000000d6340df8> (a java.lang.Object)
     * 	at java_concurrency.extra.SynchronizedAndLockThreadStateTest$$Lambda$2/1854731462.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     *
     * "线程1" #11 prio=5 os_prio=0 tid=0x0000000019829000 nid=0x3bd8 waiting on condition [0x000000001a92f000]
     *    java.lang.Thread.State: TIMED_WAITING (sleeping)
     * 	at java.lang.Thread.sleep(Native Method)
     * 	at java.lang.Thread.sleep(Thread.java:340)
     * 	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
     * 	at java_concurrency.extra.SynchronizedAndLockThreadStateTest.lambda$main$0(SynchronizedAndLockThreadStateTest.java:24)
     * 	- locked <0x00000000d6340df8> (a java.lang.Object)
     * 	at java_concurrency.extra.SynchronizedAndLockThreadStateTest$$Lambda$1/1922154895.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     *
     *
     */

}
