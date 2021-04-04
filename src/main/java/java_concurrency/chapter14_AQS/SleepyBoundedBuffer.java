package java_concurrency.chapter14_AQS;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/4/3 16:19
 * @Description 该类通过轮训和休眠来实现简单的阻塞和重试机制，比{@link GrumpyBoundedBuffer} 这种直接抛出异常的处理稍微好一点；
 * 但是，方法中由于线程休眠操作抛出了受检的中断异常，对于调用者来说，仍然要合理的处理线程的中断和响应
 * 结论就是：通过轮询和休眠的方式实现阻塞需要付出比较大的努力，需要一种更好的线程挂起和唤醒机制。
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    protected SleepyBoundedBuffer(Class<V> type, int capacity) {
        super(type, capacity);
    }

    /**
     *  注释掉的展示了一种错误的写法
     *  因为这种方法上同步的写法很可能导致一个put 线程进入判断后一直空耗cpu等待，而另外的take线程因为无法获取到锁而永远无法执行
     */

//    public synchronized void put(V v) throws Exception {
//        while (isFull()) {
//            TimeUnit.MILLISECONDS.sleep(100);
//        }
//        doPut(v);
//    }
//
//    public synchronized V take() throws Exception {
//        while (isFull()) {
//            TimeUnit.MILLISECONDS.sleep(100);
//        }
//        return doTake();
//    }

    public  void put(V v) throws InterruptedException {
        while (true){
            synchronized (this){
                if(!isFull()){
                    doPut(v);
                    return;
                }
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    public  V take() throws InterruptedException {
        while (true){
            synchronized (this){
                if(!isEmpty()){
                    return doTake();
                }
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}
