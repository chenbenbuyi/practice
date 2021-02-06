package java_concurrency.chapter7;

/**
 * @author chen
 * @date 2021/2/2 23:55
 * @Description 最原始的两种线程创建方式 继承Thread 类，实现Runnable接口
 */
public class ThreadCreateTest {

    /**
     *  请问：new Thread1().start()和 new Thread(new Thread1()).start() 有什么区别？ 下面的测试比较告诉你答案——
     *  跟踪源码你会发现，在创建线程对象的过程中，线程类 Thread 无论传入的是基于 Runnable 接口还是继承 Thread类的对象，最终都会赋值给Thread类中的变量名为target的 Runnable类型的成员变量，而该target成员变量正是需要在Thread线程对象中执行的任务。
     *  这里需要要厘清线程和任务之间的关系——Thread类本质上并不表示一个任务，它只是Java中的一个(线程)对象类，创建一个线程对象和创建一个其它类型对象并无本质区别，此时只是在Java虚拟机堆创建了线程对象，但在操作系统层面，线程并没有真正被创建。
     *  只有调用Thread类的start方法，执行本地方法，正式开启一个线程，操作系统才会正式创建任务线程，并在获得CPU时间片时，本地方法实现代码又会调用Java线程对象Thread的run方法，最终调用 target 的run方法执行线程任务。
     *
     */
    public static void main(String[] args) {
        /**
         *  方式 1 的方式 target 为 null,不会调用Thread对象继承的 run方法，方式 2 和 3 赋值target，会调用Thread类中的run方法
         */
        new Thread1("继承Thread类创建的线程").start();  // 方式 1
//        new Thread(new Thread1("继承Thread类创建的线程"),"奇怪的方式开启的线程").start();  // 方式 2
//        new Thread(new Thread2(),"实现Runnable接口创建线程").start();  // 方式 3
    }

    static class Thread1 extends Thread{

        public Thread1(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println("继承Thread类的方式，线程名为："+Thread.currentThread().getName());
        }
    }

    static class Thread2 implements Runnable{
        @Override
        public void run() {
            System.out.println("实现Runnable接口的方式，线程名为："+Thread.currentThread().getName());
        }
    }

}
