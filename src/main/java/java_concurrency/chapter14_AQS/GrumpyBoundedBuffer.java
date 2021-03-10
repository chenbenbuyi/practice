package java_concurrency.chapter14_AQS;

/**
 * @author chen
 * @date 2021/3/10 23:25
 * @Description 该实现是一个简单粗暴的方式，将状态依赖性通过异常传递给了调用者，虽然实现者管理状态很简单，但是调用方却要处理异常，并做好后续的重试处理等
 * 所以，在状态的前提条件未满足是，简单的抛出异常并不是一种好的实现方式，这会让调用者对状态依赖的处理逻辑复杂化
 */
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    public GrumpyBoundedBuffer(Class<V> type, int capacity) {
        super(type, capacity);
    }

    public synchronized void put(V v) throws Exception {
        if (isFull()) {
            throw new Exception("容量已满");
        }
        doPut(v);
    }

    public synchronized V take() throws Exception {
        if (isEmpty()) {
            throw new Exception("容量为空");
        }
        return doTake();
    }
}
