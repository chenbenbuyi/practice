package thinckingInJava.part21_juc.p7;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author chen
 * @date 2020/8/9 17:34
 * @Description 优先级队列测试
 */
public class PriorityBlockingQueueDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 100, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        PriorityBlockingQueue queue = new PriorityBlockingQueue();
        System.out.println("开启生产者线程");
        executor.execute(new PrioritzedProducer(queue, executor));
        System.out.println("开启消费者线程");
        executor.execute(new PrioritizedCustomer(queue));
    }
}

class PrioritizedTast implements Runnable, Comparable<PrioritizedTast> {
    private static int count = 0;
    private final int id = count++;
    private Random rand = new Random(47);
    private final int priority;
    private static List<PrioritizedTast> list = new ArrayList<>();

    public PrioritizedTast(int priority) {
        this.priority = priority;
        list.add(this);
    }

    @Override
    public int compareTo(PrioritizedTast o) {
        return Integer.compare(o.priority, priority);
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(250));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "PrioritizedTast{" +
                "id=" + id +
                ", 优先级：" + priority +
                '}';
    }

    public String summary() {
        return "(" + id + ":" + priority + ")";
    }

    public static class EndSentinel extends PrioritizedTast {

        private ExecutorService exec;

        public EndSentinel(ExecutorService exec) {
            super(-1);
            this.exec = exec;

        }

        @Override
        public void run() {
            int count = 0;
            for (PrioritizedTast prioritizedTast : list) {
                System.out.println("当前对象优先级：" + prioritizedTast.summary());
                if (++count % 5 == 0)
                    System.out.println();
            }
            System.out.println();
            System.out.println(this + " 关闭线程池");
            exec.shutdownNow();
        }
    }
}

class PrioritzedProducer implements Runnable {

    private Random rand = new Random(47);
    private Queue<Runnable> queue;
    private ExecutorService exec;

    public PrioritzedProducer(Queue<Runnable> queue, ExecutorService exec) {
        this.queue = queue;
        this.exec = exec;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            queue.add(new PrioritizedTast(i));
        }
        queue.add(new PrioritizedTast.EndSentinel(exec));

        System.out.println("生产者任务完成队任务插入" + queue);
    }
}

class PrioritizedCustomer implements Runnable {
    private PriorityBlockingQueue<Runnable> queue;

    public PrioritizedCustomer(PriorityBlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println("从优先级队列中获取任务1 ，队列中的任务数："+queue.size());
                PrioritizedTast take = (PrioritizedTast) queue.take();
                take.run();
                System.out.println("从优先级队列中获取任务2" + take);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("消费者线程完成队列消费");
    }
}

