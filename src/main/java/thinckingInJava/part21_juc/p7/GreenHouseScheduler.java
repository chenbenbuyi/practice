package thinckingInJava.part21_juc.p7;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/8/11 23:04
 * @Description
 */
public class GreenHouseScheduler {
    private String thermostat = "Day";

    public synchronized String getThermostat() {
        return thermostat;
    }

    public synchronized void setThermostat(String thermostat) {
        this.thermostat = thermostat;
    }

    /**
     * 其内部维护了一个作为阻塞延迟队列的嵌套类 其实现和 DelayQueue实现有很大的不同
     * DelayQueue 其内部维护了一个优先级队列
     * ScheduledThreadPoolExecutor 中内部维护的是一个嵌套的DelayedWorkQueue类，它内部维护的是 RunnableScheduledFuture
     *
     */
    private ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10);

    public void schedule(Runnable event, long delay) {
        // 创建并执行在给定延迟后启用的单次操作
        scheduler.schedule(event, delay, TimeUnit.MILLISECONDS);
    }

    public void repeat(Runnable event, long initialDelay, long period) {
        // 在给定初始时间延迟后，周期性的执行线程任务
        scheduler.scheduleAtFixedRate(event, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    // 成员内部类有外部类的引用
    class LightOn implements Runnable {
        @Override
        public void run() {
            System.out.println("开灯。。。。。。。。。。");
        }
    }

    class LightOff implements Runnable {
        @Override
        public void run() {
            System.out.println("关灯。。。。。");
        }
    }


    class WaterOn implements Runnable {
        @Override
        public void run() {
            System.out.println("开水。。。。。");
        }
    }

    class WaterOff implements Runnable {
        @Override
        public void run() {
            System.out.println("关水。。。。。");
        }
    }

    class ThermostatNight implements Runnable {
        @Override
        public void run() {
            System.out.println("恒温控制器。。晚间设置。。。");
            setThermostat("Night");
        }

    }

    class ThermostatDay implements Runnable {
        @Override
        public void run() {
            System.out.println("恒温控制器。。白天设置。。。");
            setThermostat("Day");
        }
    }

    class Bell implements Runnable {
        @Override
        public void run() {
            System.out.println("哔哩哔哩");
        }
    }


    class Terminate implements Runnable {
        @Override
        public void run() {
            System.out.println("结束咯。。。");
            scheduler.shutdownNow();
            new Thread(() -> {
                for (DataPoint d : data) {
                    System.out.println(d);
                }
            }).start();
        }
    }

    static class DataPoint {
        final Calendar time;
        final float temperature;
        final float humidity; // 湿度

        public DataPoint(Calendar time, float temperature, float humidity) {
            this.time = time;
            this.temperature = temperature;
            this.humidity = humidity;
        }

        @Override
        public String toString() {
            // %n$mf：代表输出的是浮点数，n代表是第几个参数，设置m的值可以控制小数位数
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getTime()) + String.format(" temperature: %1$.1f humidity: %2$.2f ", temperature, humidity);
        }
    }

    private Calendar lastTime = Calendar.getInstance();

    // 代码块 先于构造方法执行
    {
        lastTime.set(Calendar.MINUTE, 30);
        lastTime.set(Calendar.SECOND, 0);
    }

    private float lastTemp = 65.0f;
    private int tempDirection = +1;
    private float lastHumidity = 50.0f;
    private int humidityDirection = +1;
    private Random rand = new Random(47);
    // 创建同步容器
    private List<DataPoint> data = Collections.synchronizedList(new ArrayList<>());

    class CollectData implements Runnable {

        @Override
        public void run() {
            System.out.println("收集data 数据");
            // 注意 GreenHouseScheduler.this 中的this 写法，主要用在内部类中用来指定外围类的 this 引用
            synchronized (GreenHouseScheduler.this) {
                // 设置时间添加 30 分钟
                lastTime.set(Calendar.MINUTE, lastTime.get(Calendar.MINUTE) + 30);
                if (rand.nextInt(5) == 4)
                    tempDirection = -tempDirection;
                lastTemp = lastTemp + tempDirection * (1.0f + rand.nextFloat());
                if (rand.nextInt(5) == 4)
                    humidityDirection = -humidityDirection;
                lastHumidity = lastHumidity + humidityDirection * rand.nextFloat();
                data.add(new DataPoint((Calendar) lastTime.clone(), lastTemp, lastHumidity));
            }
        }
    }

    /**
     * 参考资料 https://www.cnblogs.com/hanganglin/p/3526240.html
     * Timer（TimerTask 继承Runnable）和ScheduledThreadPoolExecutor 都有定时调度功能 JDK1.5之后定时任务推荐使用ScheduledThreadPoolExecutor
     * Timer对调度的支持是基于绝对时间的，因此任务对系统时间的改变是敏感的；而ScheduledThreadPoolExecutor支持相对时间。
     * Timer使用单线程方式来执行所有的TimerTask，如果某个TimerTask很耗时则会影响到其他TimerTask的执行；而ScheduledThreadPoolExecutor则可以构造一个固定大小的线程池来执行任务。
     * Timer不会捕获由TimerTask抛出的未检查异常，故当有异常抛出时，Timer会终止，导致未执行完的TimerTask不再执行，新的TimerTask也不能被调度；ScheduledThreadPoolExecutor对这个问题进行了妥善的处理，不会影响其他任务的执行。
     */
    public static void main(String[] args) {
        GreenHouseScheduler gh = new GreenHouseScheduler();
        // 5000 毫秒后执行 Terminate 结束任务
        gh.schedule(gh.new Terminate(), 5000);
        // 每个1000毫秒执行一次 bell
        gh.repeat(gh.new Bell(), 0, 1000);
        gh.repeat(gh.new ThermostatNight(), 0, 2000);
        gh.repeat(gh.new LightOn(), 0, 200);
        gh.repeat(gh.new LightOff(), 0, 400);
        gh.repeat(gh.new WaterOn(), 0, 600);
        gh.repeat(gh.new WaterOff(), 0, 800);
        gh.repeat(gh.new ThermostatDay(), 0, 1400);
        gh.repeat(gh.new CollectData(), 500, 500);
    }

}



