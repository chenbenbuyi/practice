package thinckingInJava.part21_juc.p5.optimize;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chen
 * @date 2021/1/24 22:51
 */
public class Memoizer1<A, V> implements Computable<A, V> {
    // 利用普通map来做缓存。当线程下完全可以，但是在并发情况下，就要自己去保证线程安全性
    private final Map<A, V> cache = new HashMap<>();
    private final Computable<A, V> c;

    public Memoizer1(Computable<A, V> c) {
        this.c = c;
    }

    /**
     *  利用在方法上的同步，来实现线程安全，但是性能堪忧
     */
    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V v = cache.get(arg);
        if (v == null) {
            v = c.compute(arg);
            cache.put(arg, v);
        }
        return v;
    }
}
