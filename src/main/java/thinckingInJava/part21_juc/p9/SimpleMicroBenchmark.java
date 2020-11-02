package thinckingInJava.part21_juc.p9;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chen
 * @date 2020/8/31 22:38
 * @Description  关于微基准测试 在本示例中展现出的问题：
 *  ① 对于同步的测试脱离并发的上下文环境，而只有在并发的情况下，才能测试出不同的同步方式的性能差异
 *  ② 基于确切的代码（数字、关键字，可预测的计算结果等）进行的测试，很可能被编译器优化执行，从而误导测试结果
 */

public class SimpleMicroBenchmark {

    static long test(Incrementable incrementable) {
        long start = System.nanoTime();
        for (int i = 0; i < 1000000L; i++) {
            incrementable.inrement();
        }
        return System.nanoTime() - start;
    }

    public static void main(String[] args) {
        long synchTime = test(new SynchroizingTest());
        long lockTime = test(new LockingTest());
        // 整数 %[index$][标识][最小宽度]转换方式 和浮点数 %[index$][标识][最少宽度][.精度]
        // %百分号前缀 1 表示参数索引 $ 分隔符 10d 表示至少10位长度 d 十进制  o 表示八进制 x十六进制
        System.out.println(String.format("synchronized 同步 耗时：%1$10d 毫秒" , synchTime));
        System.out.println(String.format("lock 同步 耗时：%1$10d 毫秒" , lockTime));
    }
}

abstract class Incrementable {

    protected long counter = 0;

    public abstract void inrement();
}

class SynchroizingTest extends Incrementable {

    @Override
    public synchronized void inrement() {
        counter++;
    }
}

class LockingTest extends Incrementable {
    private Lock lock = new ReentrantLock();

    @Override
    public void inrement() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }
}

