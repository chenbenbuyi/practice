package daily.extra;

import thinckingInJava.part21_juc.p7.GreenHouseScheduler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @date: 2021/1/12 9:32
 * @author: chen
 * @desc: 再次声明：不推荐使用 Timer来做定时任务调度，而应该用 ScheduledExecutorService 进行代替
 * {@link GreenHouseScheduler}
 */
public class TimerTest {

    /**
     * 代码规范插件已经提示慎用 TimeTask 进行多线程任务
     * 线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中之一没有捕获抛出的异常，其它任务便会自动终止运行，
     * 使用ScheduledExecutorService则没有这个问题。
     */
    public static void main(String[] args) throws InterruptedException {
        /**
         *  schedule(TimerTask task, long delay)  在指定的延迟之后安排指定的任务执行。
         *  直接运行，timer子线程线程不会停止.因为schedule实际上是将任务加入等待队列—又是队列的应用
         *  新加入的任务会排队串行执行
         */
        Timer timer = new Timer();
        timer.schedule(new MyTask(), 1000);
        timer.schedule(new MyTask(), 1000);
        System.out.println("主线程执行流");

        /**
         *  测试Timer 任务抛出异常后续任务无法继续执行
         */
        timer.schedule(new ThrowTask(), 2000);
        TimeUnit.SECONDS.sleep(5);
        timer.schedule(new MyTask(), 2000);
    }


    /**
     * 注意，在在主方法（静态方法）中，无法访问该类的的非静态属性，包括非静态的内部类，所以如果定义的是内部类的话，需要用 static 修饰。
     * 除非将MyTask类 移出到TimerTest 类的外侧，虽然声明在同一个类文件中，但和两个类无异
     */
    static class MyTask extends TimerTask {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("timer子线程任务");
            //  Timer 运行多个任务是，只要任务之一没有捕获抛出的异常，其它后继任务便会自动终止运行
//            int i = 1/0;
        }
    }

    static class ThrowTask extends TimerTask {
        @Override
        public void run() {
            throw new RuntimeException();
        }
    }
}


