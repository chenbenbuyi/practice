package thinckingInJava.part21_juc.extra;

/**
 * @author chen
 * @date 2020/8/3 7:12
 * @Description 两次开启同一个线程，会发生什么
 */
public class ThreadTwoStartTest implements Runnable {
    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadTwoStartTest());
        thread.start();
        thread.start();
    }
    @Override
    public void run() {
        System.out.println("线程运行");
    }
}
