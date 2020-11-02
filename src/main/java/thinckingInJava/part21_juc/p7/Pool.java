package thinckingInJava.part21_juc.p7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author chen
 * @date 2020/8/12 23:17
 * @Description 信号量 常用于限制可以访问某些资源的线程数量，例如通过 Semaphore 限流。
 *  Semaphore是一种在多线程环境下使用的设施，该设施负责协调各个线程，以保证它们能够正确、合理的使用公共资源的设施，也是操作系统中用于控制进程同步互斥的量。
 *  Semaphore是一种计数信号量，用于管理一组资源，内部是基于AQS的共享模式。它相当于给线程规定一个量从而控制允许活动的线程数
 *
 *  以一个停车场是运作为例。
 *      为了简单起见，假设停车场只有三个车位，一开始三个车位都是空的。
 *      这时如果同时来了五辆车，看门人允许其中三辆不受阻碍的进入，然后放下车拦，剩下的车则必须在入口等待，此后来的车也都不得不在入口处等待。
 *      这时，有一辆车离开停车场，看门人得知后，打开车拦，放入一辆，如果又离开两辆，则又可以放入两辆，如此往复。
 *      这个停车系统中，每辆车就好比一个线程，看门人就好比一个信号量，看门人限制了可以活动的线程。假如里面依然是三个车位，但是看门人改变了规则，要求每次只能停两辆车，那么一开始进入两辆车，后面得等到有车离开才能有车进入，但是得保证最多停两辆车。
 *      对于Semaphore类而言，就如同一个看门人，限制了可活动的线程数
 */
public class Pool<T> {
    private int size;
    private List<T> items = new ArrayList<>();
    private volatile boolean[] checkedOut;

    /**
     * Semaphore 管理一组虚拟许可，通过构造器指定初始数量
     * 执行操作是获取许可，执行完之后释放许可，如果没有许可可用，acquire 将阻塞
     *
     */
    private Semaphore available;

    public Pool(Class<T> clz, int size) {
        this.size = size;
        checkedOut = new boolean[size];
        available = new Semaphore(size, true);
        for (int i = 0; i < size; i++) {
            try {
                items.add(clz.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public T checkOut() throws InterruptedException {
        System.out.println("进入此方法1");
        available.acquire();
        System.out.println("进入此方法2");
        return getItem();
    }

    public void checkIn(T t) {
        if (releaseItem(t)) {
            available.release();
        }
    }

    private synchronized T getItem() {
        for (int i = 0; i < size; i++) {
            if (!checkedOut[i]) {
                checkedOut[i] = true;
                return items.get(i);
            }
        }
        // 信号量阻止，代码无法运行到此处
        return null;
    }

    private synchronized boolean releaseItem(T item) {
        int i = items.indexOf(item);
        if (i == -1) return false;
        if (checkedOut[i]) {
            checkedOut[i] = false;
            return true;
        }
        return false;
    }


}
