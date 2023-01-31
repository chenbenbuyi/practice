package skill.scheduler;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.Scheduler;
import cn.hutool.cron.task.Task;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author csxian
 * @Description 比较轻量的定时任务测试
 * @Date 2023/1/31 16:32
 */
public class HutoolSchedulerTest {

    private static Scheduler scheduler = CronUtil.getScheduler();

    public static void main(String[] args) throws InterruptedException {
        scheduler.schedule("0/2 * * * * ?", (Task) () -> {
            System.out.println("打印时间:" + LocalDateTime.now());
        });
        //支持秒级别定时任务
        scheduler.setMatchSecond(true);
        // 开启定时任务
        scheduler.start();

        TimeUnit.SECONDS.sleep(10000L);
    }
}
