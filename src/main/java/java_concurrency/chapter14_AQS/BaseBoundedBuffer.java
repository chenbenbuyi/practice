package java_concurrency.chapter14_AQS;

import java.lang.reflect.Array;

/**
 * @author chen
 * @date 2021/3/6 19:58
 * @Description
 * 对于状态依赖性的操作可以有多种处理方式，可以简单粗暴的直接抛出异常中断执行，或者温柔一点返回一些错误状态，或者保持阻塞直到条件满足。
 *  例如，在Queue中提供了两种方式，poll方法在队列为空时返回null,remove方法则会抛出运行时异常
 */
public abstract class BaseBoundedBuffer<V> {
    // 基于泛型数组的有界缓存
    private final V[] buf;
    private int tail;
    private int head;
    private int count;

    /**
     * 因为无法直接 new  泛型数组对象，所以只能通过Object对象数组进行强转
     * 或者通过 Array.newInstance(type, capacity)的方式创建泛型对象数组
     */
    protected BaseBoundedBuffer(Class<V> type, int capacity) {
        this.buf = (V[]) Array.newInstance(type, capacity);
//        this.buf = (V[]) new Object[capacity];
    }

    protected final synchronized  void doPut(V v) {
        buf[tail] = v;
        if (++tail == buf.length) {
            tail = 0;
        }
        ++count;
    }

    protected final synchronized  V doTake() {
        V v = buf[head];
        buf[head] = null;
        if (++head == buf.length) {
            head = 0;
        }
        --count;
        return v;
    }

    public final synchronized  boolean isFull() {
        return count == buf.length;
    }

    public final synchronized  boolean isEmpty() {
        return count == 0;
    }
}
