package leetcode.concurrency;

import java_concurrency.chapter5.SemaphoreTest;

import java.util.concurrent.Semaphore;

/**
 * 链接：https://leetcode-cn.com/problems/print-in-order
 * 题目描述
 * <p>
 * 我们提供了一个类：
 * public class Foo {
 *   public void first() { print("first"); }
 *   public void second() { print("second"); }
 *   public void third() { print("third"); }
 * }
 * 三个不同的线程 A、B、C 将会共用一个 Foo 实例。
 * <p>
 * 一个将会调用 first() 方法
 * 一个将会调用 second() 方法
 * 还有一个将会调用 third() 方法
 * 请设计修改程序，以确保 second() 方法在 first() 方法之后被执行，third() 方法在 second() 方法之后被执行。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [1,2,3]
 * 输出: "firstsecondthird"
 * 解释:
 * 有三个线程会被异步启动。
 * 输入 [1,2,3] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 second() 方法，线程 C 将会调用 third() 方法。
 * 正确的输出是 "firstsecondthird"。
 * 示例 2:
 * <p>
 * 输入: [1,3,2]
 * 输出: "firstsecondthird"
 * 解释:
 * 输入 [1,3,2] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 third() 方法，线程 C 将会调用 second() 方法。
 * 正确的输出是 "firstsecondthird"。
 */
public class PrintInOrder {

    class SemaphoreFoo {

        /**
         * 利用 信号量
         * 关键点：许可类似于生产出来的，可以比初始化许可多 {@link SemaphoreTest}
         */
        private Semaphore second = new Semaphore(0);
        private Semaphore third = new Semaphore(0);

        public SemaphoreFoo() {
        }

        public void first(Runnable printFirst) throws InterruptedException {
            printFirst.run();
            second.release();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            second.acquire();
            printSecond.run();
            third.release();
        }

        public void third(Runnable printThird) throws InterruptedException {
            third.acquire();
            printThird.run();
        }
    }


    class WaitNotifyFoo {

        /**
         *   发现有些解答在使用等待通知机制的时候，既然方法内用了同步关键字synchronized，竟然还将变量声明为 volatile ，其实大可不必
         *   因为内置锁比volatile 有更强的同步语义，足以保证线程间的可见性
         */
//        private volatile int flag = 0;
        private  int flag = 0;

        public WaitNotifyFoo() {
        }

        public void first(Runnable printFirst) throws InterruptedException {
            // 此处同步的作用主要是在同步块内通知唤醒其它的挂起的线程，否则会出现另外两个线程无线等待的情况
            printFirst.run();
            synchronized (this) {
                flag++;
                this.notifyAll();
            }
        }

        public void second(Runnable printSecond) throws InterruptedException {
            synchronized (this) {
                while (flag != 1) {
                    this.wait();
                }
                flag++;
                // 注意这里必须在同步块内执行，在同步块外执行在并发环境下，很可能无法保证打印的顺序
                printSecond.run();
                this.notifyAll();
            }
        }

        public void third(Runnable printThird) throws InterruptedException {
            synchronized (this) {
                while (flag != 2) {
                    this.wait();
                }
            }
            printThird.run();
        }
    }
}
