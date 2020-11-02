package thinckingInJava.part21_juc.p2;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/4/6 9:58
 * @Description
 */
public class DaemonFinally implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(0);
            System.out.println(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println("后台线程：在不运行finally 就会停止");
        }
    }


    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonFinally());
//        thread.setDaemon(true);
        thread.start();
    }




}
