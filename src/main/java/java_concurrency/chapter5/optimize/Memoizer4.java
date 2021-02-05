package java_concurrency.chapter5.optimize;

import java.util.concurrent.*;

/**
 * @author chen
 * @date 2021/1/24 22:51
 */
public class Memoizer4<A, V> implements Computable<A, V> {
    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer4(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        while (true) {
            Future<V> v = cache.get(arg);
            if (v == null) {
                Callable<V> eval = () -> c.compute(arg);
                FutureTask<V> vFutureTask = new FutureTask<>(eval);
                v = cache.putIfAbsent(arg, vFutureTask);
                /**
                 *  ConcurrentMap并发Map 中的 putIfAbsent 方法提供原子性的若没有则添加操作
                 *  putIfAbsent 方法中，如果传入key对应的value已经存在，就返回存在的value，不进行替换。如果不存在，就添加key和value，返回null
                 *  此处类似于双检锁的操作，
                 */
                if (v == null) {
                    v = vFutureTask;
                    vFutureTask.run();
                }
            }
            try {
                return v.get();
                // CancellationException 异常表示由于任务被取消，无法检索值生成任务的结果，这个时候应该移除缓存
            } catch (CancellationException e) {
                cache.remove(arg, v);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
