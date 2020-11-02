package thinckingInJava.part21_juc.p3.p3_5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chen
 * @date 2020/5/17 15:58
 * @Description
 */
abstract class PairManager {

    AtomicInteger checkCounter = new AtomicInteger(0);
    protected Pair p = new Pair(); // 构造非线程安全类对象，同时初始x y的值为 0

    // 获取线程安全的同步列表
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<>());

    // 注意该方法是被同步的，也就意味后后续对Pair的获取是在线程同步的环境下进行。
    // 如果不进行同步，虽然在 increment 方法进行了同步操作，但线程在获取Pair的时候，就有极大可能Pair的x和y处于不一致的状态
    public synchronized Pair getPair() {
        return new Pair(p.getX(), p.getY());
    }

    void store(Pair p) {
        storage.add(p);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void increment();
}


class PairManger1 extends PairManager {

    // 同步方法保证p对象中对x和y的自增操作是原子性的
    @Override
    public synchronized void increment() {
        p.incrementX();
        p.incrementY();
        store(getPair());
    }
}

class PairManger2 extends PairManager {

    @Override
    public void increment() {
        Pair temp;
        // 获取内部类对象的互斥锁，构建一个同步临界区（代码块），这样保证对Pair 对象 x和y自增操作同步的同时，又不影响并发线程对 Pair 对象实例的存储操作
        synchronized (this) {
            p.incrementX();
            p.incrementY();
            temp = getPair();
        }
        store(temp);
    }
}
// 该线程对象专门用来对 PairManager 类型的子类对象进行自增操作
class PairManipulator implements Runnable {

    private PairManager pm;

    public PairManipulator(PairManager pm) {
        this.pm = pm;
    }

    @Override
    public void run() {
        while (true) {
            pm.increment();
        }
    }

    @Override
    public String toString() {
        return "Pair{" +
                "pm=" + pm.getPair() +
                "checkCounter=" + pm.checkCounter.get() +
                '}';
    }
}
// 该线程对象用来进行计数
class PairChecker implements Runnable {

    private PairManager pm;

    public PairChecker(PairManager pm) {
        this.pm = pm;
    }

    @Override
    public void run() {
        while (true) {
            // incrementAndGet 自增操作是原子性的 在每次chekState 成功都进行递增
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}

public class CriticalSection {
    static void testApproaches(PairManager pman1, PairManager pman2) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 50L, TimeUnit.SECONDS, new SynchronousQueue<>());

        // pman1 的自增操作是同步整个方法
        // pman2 的自增操作是同步方法局部，不影响通过集合的并发存储
        PairManipulator
                pm1 = new PairManipulator(pman1),
                pm2 = new PairManipulator(pman2);
        PairChecker
                pairChecker1 = new PairChecker(pman1),
                pairChecker2 = new PairChecker(pman2);

        executor.execute(pm1);
        executor.execute(pm2);
        executor.execute(pairChecker1);
        executor.execute(pairChecker2);
        try {
            // 有 5 秒的时间来执行线程
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("pm1=" + pm1 + "\npm2=" + pm2);
        System.exit(0);
    }

    public static void main(String[] args) {
        PairManager
                pairManager1 = new PairManger1(),
                pairManager2 = new PairManger2();
        PairManager
                lockPairManager1 = new Lock1PairManager(),
                lockPairManager2 = new Lock2PairManager();
//        testApproaches(pairManager1,pairManager2);
        testApproaches(lockPairManager1,lockPairManager2);
    }
}

