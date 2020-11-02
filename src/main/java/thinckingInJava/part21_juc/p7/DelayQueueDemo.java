package thinckingInJava.part21_juc.p7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * @author chen
 * @date 2020/8/3 22:30
 * @Description DelayedTaskConsumer 将到期时间最长的任务从队列中取出运行
 * DelayQueue 队列中的元素只能在其延迟到期后在被使用，如果没有延迟到期，poll将 返回null,不允许空元素
 *  延迟队列，根据比较规则，队列中的任务按照延迟时间的长短依次执行任务，和放入队列的顺序无关！！！
 */


public class DelayQueueDemo {
    public static void main(String[] args) {
        Random random = new Random(47);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 30, TimeUnit.SECONDS, new SynchronousQueue<>());
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        for (int i = 0; i < 20; i++) {
            queue.put(new DelayedTask(random.nextInt(800)));
        }
        // 将该嵌套类对象放置为最后一个元素，以便关闭所有的事物
        queue.add(new DelayedTask.EndSentinel(5000, executor));
        executor.execute(new DelayedTaskConsumer(queue));
    }
}

class DelayedTaskConsumer implements Runnable {

    private DelayQueue<DelayedTask> q;


    public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
        this.q = q;
    }

    /**
     * 消费者线程从队列中取出线程任务执行
     */
    @Override
    public void run() {
        try {
            while (!Thread.interrupted())
                q.take().run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("DelayedTaskConsumer 任务执行结束");
    }
}

/**
 * 　java延迟队列提供了在指定时间才能获取队列元素的功能，队列头元素是最接近过期的元素。
 *  没有过期元素的话，使用poll()方法会返回null值，超时判定是通过getDelay(TimeUnit.NANOSECONDS)方法的返回值小于等于0来判断。该延时队列不能存放空元素。
 */
class DelayedTask implements Delayed, Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence = new ArrayList<>();

    public DelayedTask(int delayTime) {
        delta = delayTime;
        // 纳秒转换位毫秒 NANOSECONDS.convert(delta, MILLISECONDS) --希望转换的单位声明调用，原始单位类型做参数
        trigger = System.nanoTime() + NANOSECONDS.convert(delta, MILLISECONDS);
        sequence.add(this);
    }

    // getDelay(TimeUnit unit)  在给定的时间单位中返回与此对象相关联的剩余延迟。
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), NANOSECONDS);
    }

    // getDelay(TimeUnit unit)接口的实现必须定义一个compareTo方法，提供与其getDelay方法一致的排序。
    @Override
    public int compareTo(Delayed arg) {
        DelayedTask that = (DelayedTask) arg;
        if (trigger < that.trigger) return -1;
        if (trigger > that.trigger) return 1;
        return 0;
    }

    @Override
    public void run() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "DelayedTask{" +
                "id=" + id +
                ", delta=" + delta +
                '}';
    }

    public String summary() {
        return "(" + id + ":" + delta + ")";
    }

    public static class EndSentinel extends DelayedTask {
        private ExecutorService exec;

        public EndSentinel(int delayTime, ExecutorService e) {
            super(delayTime);
            exec = e;
        }

        @Override
        public void run() {
            for (DelayedTask delayedTask : sequence) {
                System.out.println(delayedTask.summary() + " ");
            }
            System.out.println(this + " 线程池关闭");
            exec.shutdownNow();
        }
    }
}
