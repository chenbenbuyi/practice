package java_concurrency.chapter5.optimize;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author chen
 * @date 2021/1/24 22:51
 */
public class Memoizer3<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer3(Computable<A, V> c) {
        this.c = c;
    }

    /**
     *
     */
    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> v = cache.get(arg);
        if (v == null) {
            // 缓存中没有，就开启一个任务线程
            Callable<V> eval = () -> c.compute(arg);
            FutureTask<V> vFutureTask = new FutureTask<>(eval);
            v = vFutureTask;
            /**
             *  任务是异步执行的，所以可以很快的将任务缓存起来，这样，当另一个线程切换执行且获取同样的键时，大概率不会再执行该判断内的代码，不会重复计算
             *  但是呢，由于if判断任务不是原子性的，所以线程安全性和Memoizer2中类似，仍然有先检查后执行的问题，还是可能重复计算耗时任务
             */
            cache.put(arg, vFutureTask);
            vFutureTask.run();
        }
        try {
            return v.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(() -> {
            TimeUnit.SECONDS.sleep(3);
            return "测试返回值";
        });
        task.run();
        String s = task.get();
        System.out.println(s);
    }
}
