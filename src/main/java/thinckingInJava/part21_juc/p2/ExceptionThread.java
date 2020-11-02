package thinckingInJava.part21_juc.p2;

import java.util.concurrent.*;

/**
 * @author chen
 * @date 2020/4/8 23:17
 * @Description
 */
public class ExceptionThread implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException("线程异常");
    }


    public static void main(String[] args) {
//        try {
//            Thread thread = new Thread(new ExceptionThread());
//            thread.start();
//        } catch (Exception e) {
//            // 该语句不会打印，因为无法捕获从线程中逃逸的异常
//            System.out.println("捕获线程内部抛出的异常");
//        }


//        try {
//            Thread thread = new Thread(new ExceptionThread2());
//            thread.start();
//        } catch (Exception e) {
//            // 该语句不会打印，因为无法捕获从线程中逃逸的异常
//            System.out.println("捕获线程内部抛出的异常");
//        }

        ExecutorService executorService = new ThreadPoolExecutor(1,1,0L, TimeUnit.SECONDS,new LinkedBlockingQueue<>(1),new HandlerThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        executorService.execute(new ExceptionThread2());
        executorService.shutdown();

        // 如果你确定将要在代码中处处使用相同的异常处理程序，更简单的方式是使用静态域的方式设置异常处理器.如果单个线程设置了自己的异常处理程序，将会覆盖公共的异常处理程序
//        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

    }


    static class ExceptionThread2 implements Runnable{

        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            System.out.println("当前线程1："+thread.hashCode());
//            thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
            int i = 1/0;
            System.out.println("发生异常后");
        }
    }


    //  Thread.UncaughtExceptionHandler 当Thread由于未捕获的异常而突然终止时，处理程序的接口被调用。
    static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("捕获的异常："+e);
        }
    }

    // 通过线程工厂的方式创建线程

    static class HandlerThreadFactory implements ThreadFactory{

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            System.out.println("当前线程2："+thread.hashCode());
            thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
            return thread;
        }
    }
}
