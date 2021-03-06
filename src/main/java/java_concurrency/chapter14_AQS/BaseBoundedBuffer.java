package java_concurrency.chapter14_AQS;

import java.lang.reflect.Array;

/**
 * @author chen
 * @date 2021/3/6 19:58
 * @Description
 */
public abstract class BaseBoundedBuffer<V> {
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

    protected synchronized final void doPut(V v) {
        buf[tail] = v;
        if (++tail == buf.length) {
            tail = 0;
        }
        ++count;
    }

    protected synchronized final V doTake() {
        V v = buf[head];
        buf[head] = null;
        if (++head == buf.length) {
            head = 0;
        }
        --count;
        return v;
    }

    public synchronized final boolean isFull() {
        return count == buf.length;
    }

    public synchronized final boolean isEmpty() {
        return count == 0;
    }
}
