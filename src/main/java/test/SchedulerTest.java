package test;


import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description 定时任务测试
 * @Date 2021/11/19 10:24
 * @Created by csxian
 */
public class SchedulerTest {

    private ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(100);

    public void schedule(Runnable event, long delay) {
        // 创建并执行在给定延迟后启用的单次操作
        scheduler.schedule(event, delay, TimeUnit.SECONDS);
    }

    public void repeat(Runnable event, long initialDelay, long period) {
        // 在给定初始时间延迟后，周期性的执行线程任务
        scheduler.scheduleAtFixedRate(event, initialDelay, period, TimeUnit.SECONDS);
    }

    @Test
    public void test() throws InterruptedException {
        for (int i = 1; i < 101; i++) {
            String s = i + "";
            this.schedule(() -> {
                System.out.println("执行第" + s + "个监控任务,时间：" + LocalDateTime.now());
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, 5);
        }

        TimeUnit.SECONDS.sleep(100);
    }
}
