package thinckingInJava.part21_juc.p9;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chen
 * @date 2020/9/1 22:51
 * @Description 原则
 * 虽然 Lock相较于 synchronized 要高效，但基于可读性和易用性考虑，优先使用synchronized 同步关键字，只有在性能调优的时候，才替换为Lock
 */
public class SynchronizationComparisons {

    // BaseLine 不加锁的情况下，作为对比的基准
    static BaseLine baseLine = new BaseLine();
    static SynchroniedTest synch = new SynchroniedTest();
    static LockTest lock = new LockTest();
    static AtomicTest atomic = new AtomicTest();

    static void test() {
        System.out.println("============================");
        System.out.println(String.format("%-12s: %13d\n", "Cycles", Accumulator.cycles));
        synch.timedTest();
        lock.timedTest();
        atomic.timedTest();
        Accumulator.report(synch, baseLine);
        Accumulator.report(lock, baseLine);
        Accumulator.report(atomic, baseLine);
        Accumulator.report(synch, lock);
        Accumulator.report(synch, atomic);
        Accumulator.report(lock, atomic);
    }

    public static void main(String[] args) {
        int iterations = 5;
        baseLine.timedTest();
        for (int i = 0; i < iterations; i++) {
            test();
            // 每次重复，循环的数量都会增加，用以比较运行次数的增加，不同互斥情况的性能
            Accumulator.cycles *= 2;
        }
        Accumulator.exec.shutdown();
    }
}

abstract class Accumulator {
    public static long cycles = 50000L;
    private static final int N = 4;
    static ExecutorService exec = new ThreadPoolExecutor(2 * N, 2 * N,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    private static CyclicBarrier barrier = new CyclicBarrier(2 * N + 1);
    protected volatile int index = 0;
    protected volatile long value = 0;
    protected long duration = 0; // 记录执行时间
    protected String id = "error";
    protected static final int SIZE = 100000; //数组大小
    protected static int[] preLoaded = new int[SIZE];

    static {
        // 数组填充
        Random random = new Random(47);
        for (int i = 0; i < SIZE; i++) {
            preLoaded[i] = random.nextInt();
        }
    }

    // 每次调用该方法，都会移动到数组的下一个位置，并将该位置的随机数值添加到当前 value
    public abstract void accumulate();

    public abstract long read();

    // Modifier 和 Reader构成在Accumulator对象上的访问竞争——因为其accumulate和read在子类的实现中是互斥的存在
    private class Modifier implements Runnable {

        @Override
        public void run() {
            for (long i = 0; i < cycles; i++) {
                accumulate();
            }
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class Reader implements Runnable {

        @Override
        public void run() {
            for (long i = 0; i < cycles; i++) {
                value = read();
            }
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void timedTest() {
        long start = System.nanoTime();
        //注意这里，循环开启线程的代码和后面barrier.await部分代码，虽然开启操作本身是同步的，但是线程任务是异步的，
        // 而且很大程度上，开启的线程任务开始执行要比开启线程操作的代码要滞后
        for (int i = 0; i < N; i++) {
            exec.execute(new Modifier());
            exec.execute(new Reader());
        }
        try {
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 通过栅栏的阻拦，只有等到线程任务都执行完成，才会执行到此处
        duration = System.nanoTime() - start;
        System.out.println("" + String.format("%-13s: %13d\n", id, duration));
    }

    public static void report(Accumulator acc1, Accumulator acc2) {
        System.out.println(String.format("%-22s: %.2f\n", acc1.id + "/" + acc2.id, (double) acc1.duration / (double) acc2.duration));
    }
}

class BaseLine extends Accumulator {

    // 构造代码块的执行先于构造器
    {
        id = "BaseLine";
    }

    @Override
    public void accumulate() {
        //先去数组index位置的值，index再进行自增
        value += preLoaded[index++];
        if (index >= SIZE) index = 0;
    }

    @Override
    public long read() {
        return value;
    }
}

class SynchroniedTest extends Accumulator {

    {
        id = "synchronized";
    }

    @Override
    public synchronized void accumulate() {
        value += preLoaded[index++];
        if (index >= SIZE) index = 0;
    }

    @Override
    public synchronized long read() {
        return value;
    }
}

class LockTest extends Accumulator {

    {
        id = "Lock";
    }

    private Lock lock = new ReentrantLock();

    @Override
    public void accumulate() {
        lock.lock();
        try {
            value += preLoaded[index++];
            if (index >= SIZE) index = 0;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long read() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }
}

class AtomicTest extends Accumulator {

    {
        id = "Atomic";
    }

    private AtomicInteger index = new AtomicInteger(0);
    private AtomicLong value = new AtomicLong(0);

    @Override
    public void accumulate() {
        // 当前值原子性加 1
        int i = index.getAndIncrement();
        /**
         * 此处数组越界是由并发引起的
         * 虽然 AtomicInteger 的相关操作本身是原子性的，但是这段组合代码却不是原子性的，而且这段本身并没有进行同步
         * 这在线程切换过程中会容易出现线程安全问题 。实际上，Atomic原子操作只适用于单个变量的原子操作
         */
        value.getAndAdd(preLoaded[i]); //以原子的方式将当前值和输入值相加并返回结果

        if (++i >= SIZE) {
            index.set(0);
        }
    }

    @Override
    public long read() {
        return value.get();
    }
}