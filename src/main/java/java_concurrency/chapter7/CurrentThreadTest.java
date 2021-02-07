package java_concurrency.chapter7;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/2/7 10:41
 * @Description 当前线程测试——谁是当前线程？ 测试会告诉你答案
 */

public class CurrentThreadTest {

    public static void main(String[] args) throws InterruptedException {
        /**
         *  继承Thread类方式创建的 MyThread 对象在构造时会调用 Thread 类的默认构造器 {@link  Thread#Thread()}，在构造器中线程名字为 "Thread-" 加上类字段threadInitNumber的递增值组成
         *  所以，new MyThread() 和 new Thread(myThread) 两句代码中，实际上是产生了两个线程对象名字，代码 1 执行时的 Thread-0 和 代码 2 执行时的 Thread-1
         *  当然，主线程和其它后台辅助线程是虚拟机帮我构造完成的，并不在此线程创建对象的讨论范畴
         *  通过开启线程的对比测试 你会发现，哪个线程对象调用的 start，虚拟机真正创建的就是哪个线程执行任务。而代码 2 这种令人疑惑的创建方式，实际上不过是将MyThread对象内部包含的线程任务委托给了新开启的线程去执行了，此时MyThread和原始的Runnable接口无异
         */
        MyThread myThread = new MyThread();  // 代码 1
        Thread thread = new Thread(myThread); // 代码 2
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("===============分界线 1 ===============");
        // 比较下两种创建线程对象方式的异同
        Thread r = new Thread(new MyThread2());
        r.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("===============分界线 2 ===============");
        myThread.start();
    }
}

/**
 *   this.getName() 获取的只不过是当前对象的名字属性的值，this语义和普通对象的this并没有区别，并不一定表示当前正在执行的线程名字
 *   只有通过 Thread.currentThread() 获取到当前真正在执行的线程，通过真正执行的线程对象获取的名字，才是真正的 当前线程名字
 */
@Slf4j
class MyThread extends Thread {
    public MyThread() {
        log.info("当前执行线程的名字：{}", Thread.currentThread().getName());
        log.info("当前线程对象的名字：{}", this.getName());
    }

    @Override
    public void run() {
        log.info("当前执行线程的名字：{} ，当前线程isAlive值={}", Thread.currentThread().getName(), Thread.currentThread().isAlive());
        log.info("当前线程对象的名字：{} ，线程对象isAlive值={}", this.getName(), this.isAlive());
    }
}

@Slf4j
class MyThread2 implements Runnable {

    @Override
    public void run() {
        log.info("当前执行线程的名字：{} ，当前线程isAlive值={}", Thread.currentThread().getName(), Thread.currentThread().isAlive());
    }
}
