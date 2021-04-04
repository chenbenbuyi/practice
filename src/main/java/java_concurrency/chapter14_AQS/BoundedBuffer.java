package java_concurrency.chapter14_AQS;


/**
 * @author chen
 * @date 2021/4/3 16:48
 * @Description
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
    protected BoundedBuffer(Class<V> type, int capacity) {
        super(type, capacity);
    }

    /**
     * 本人在写wait的时候又习惯性的写到了if语句中，再次声明，wait的调用一定是在while循环中，这也是一种最佳实践，经典写法，why?
     * 因为if语句是顺序执行，如果在if语句中，当线程被唤 醒之后，是会顺序执行剩下的语句，而不是重新判断条件是否满足，这在多线程情况下会有并发问题
     */
    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPut(v);
        notifyAll();
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }
}
