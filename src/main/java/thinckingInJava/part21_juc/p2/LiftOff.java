package thinckingInJava.part21_juc.p2;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/3/30 21:30
 * @Description
 */
public class LiftOff implements Runnable {
    private int countDown = 10;

    private static int taskCount = 0;

    private final int id = taskCount++;


    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    private String status() {
        return id+"# 发射井"  + "倒计时：(" + (countDown > 0 ? countDown : "发射！" )+ ")";
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            try {
                // 异常不能跨线程传播，所以必须在本线程中处理异常
                TimeUnit.SECONDS.sleep(1);
                System.out.println(status());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //让步的意思，根据语义理解就是线程让出当前时间片给其他线程执行,但调度器可以忽视这个通知
            //这个方法一般不推荐使用，它主要用于debug和测试程序，用来减少bug以及对于并发程序结构的设计;业务代码使用这个函数需慎重
            Thread.yield();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new LiftOff(10));
            thread.start();
        }
    }
}
