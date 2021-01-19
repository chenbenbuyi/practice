package thinckingInJava.part21_juc.p7;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * @author chen
 * @date 2020/8/13 21:38
 * @Description 利用信号量实对容器添加边界
 * 信号量用来控制同时访问某个特定资源的操作数量，可以用来实现某种资源池或者对容器添加边界。
 * 如下面的示例对Set添加边界，线程只有获取到信号量许可才能向容器添加，删除元素则释放许可
 * 注意：信号量并不局限于创建时的许可证数量，也就是说一直释放许可后实际的许可数量是可以大于初始化时创建的许可证数量
 * 信号量的许可并不和对象相关联，一个线程获取的许可可以在另一个线程中释放。
 */
public class BoundedHashSet<T> {

    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<>());
        this.sem = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                sem.release();
            }
        }
    }

    public boolean remove(T o) {
        boolean warRemoved = set.remove(o);
        if (warRemoved) {
            sem.release();
        }
        return warRemoved;
    }
}
