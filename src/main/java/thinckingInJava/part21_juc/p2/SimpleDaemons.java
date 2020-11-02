package thinckingInJava.part21_juc.p2;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/4/5 8:30
 * @Description
 */
public class SimpleDaemons implements Runnable {
    @Override
    public void run() {
        while (true){
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前线程："+Thread.currentThread()+" :"+this);
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new SimpleDaemons());
            thread.setDaemon(true);
            thread.start();
        }

        // 如果主线程结束，守护线程也会跟着消亡，程序终止； 只有非守护线程存在的时候，程序才不会终止
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
