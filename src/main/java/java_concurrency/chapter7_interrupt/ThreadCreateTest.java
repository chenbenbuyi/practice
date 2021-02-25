package java_concurrency.chapter7_interrupt;

/**
 * @author chen
 * @date 2021/2/2 23:55
 * @Description 最原始的两种线程创建方式 继承Thread 类，实现Runnable接口
 */
public class ThreadCreateTest {

    /**
     *  请问：new Thread1().start()和 new Thread(new Thread1()).start() 有什么区别？ 下面的测试比较告诉你答案——
     *  跟踪源码你会发现，在创建线程对象的过程中，线程类 Thread 无论传入的是基于 Runnable 接口还是继承 Thread类的对象，最终都会赋值给Thread类中的变量名为target的 Runnable类型的成员变量，而该target成员变量正是需要在Thread线程对象中执行的任务。
     *  这里需要要厘清线程和任务之间的关系——Thread类本质上并不表示一个任务，它只是Java中的一个线程类，创建一个线程对象和创建一个其它类型对象并无本质区别，此时只是在Java虚拟机堆创建了线程对象，但在操作系统层面，线程并没有真正被创建。
     *  只有调用Thread类的start方法，执行本地方法，正式开启一个线程，操作系统才会正式创建任务线程，并在获得CPU时间片时，本地方法实现代码又会调用Java线程对象Thread的run方法，最终调用 target 的run方法执行线程任务。
     *
     */
    public static void main(String[] args) {
        /**
         *  方式 1 的方式 target 为 null,也就是没有委托的目标线程对象，实际上此时它自己就是真正要执行的一个Runnable,在调用start方法开启操作系统线程后，底层代码会调用其继承自Runnable接口的run方法执行线程任务；
         *  方式 2 和 3 会赋值目标线程对象 target，会调用Thread类中的run方法继而执行目标线程对象target自己的run方法执行线程任务
         *  所以，总结起来就是：
         *      方式 1 是将自己当成一个实际的 Runnable 实际执行任务；
         *      方式 2 和 3 的情况都是利用外部的Thread类来新开启一个线程，新开启的线程调用其自己的run方法，其run方法中将真正的任务委托给了传入的参数 target 线程真正执行业务代码
         */
        new Thread1("继承Thread类创建的线程").start();  // 方式 1
        new Thread(new Thread1("继承Thread类创建的线程"),"奇怪的方式开启的线程").start();  // 方式 2
        new Thread(new Thread2(),"实现Runnable接口创建线程").start();  // 方式 3
    }

    static class Thread1 extends Thread{

        public Thread1(String name) {
            super(name);
        }

        @Override
        public void run() {
            /**
             *  通过构造器和run方法输出的名字对比
             *  你会发现，两种方式获取的“当前线程”的名字不一样。实际上，只有Thread.currentThread().getName() 才表示获取的当前线程名字
             *  详细请参见 {@link CurrentThreadTest}
             */
//            System.out.println("继承Thread类的方式，线程对象的名字为："+this.getName());
            System.out.println("继承Th2222222222r ead类的方式，线程名为："+Thread.currentThread().getName());
        }
    }

    static class Thread2 implements Runnable{
        @Override
        public void run() {
            System.out.println("实现Runnable接口的方式，线程名为："+Thread.currentThread().getName());
        }
    }

}
