package java_concurrency.chapter5.optimize;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chen
 * @date 2021/1/24 22:51
 */
public class Memoizer2<A, V> implements Computable<A, V> {
    // 利用ConcurrentHashMap 这种并发容器来保证线程安全
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }

    /**
     *  这种线程安全问题已经很明显了，虽然ConcurrentHashMap本身是线程安全的，但操作容器的代码由于存在竞态条件——这里有明显的先检查后执行，因此会有线程安全问题
     */
    @Override
    public  V compute(A arg) throws InterruptedException {
        V v = cache.get(arg);
        if (v == null) {
            /**
             * 由于 compute 耗时较长，在并发情况下，很可能一个线程在进行耗时计算，另一个线程并不知道，也会进行相同的耗时计算，结果就是重复计算
             * 是否可以通过某种方式表达线程一整张进行计算，这样并发线程如果查找相同的键对应的值，并发线程完全可以等待线程一的计算结果
             * 利用 FutureTask 可以满足这个条件 {@link Memoizer3}
             */
            v = c.compute(arg);
            cache.put(arg, v);
        }
        return v;
    }
}
