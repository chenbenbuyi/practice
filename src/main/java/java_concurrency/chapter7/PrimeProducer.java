package java_concurrency.chapter7;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * @author chen
 * @date 2021/2/1 22:37
 * @Description 中断线程的正确处理方式
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!Thread.currentThread().isInterrupted()){
            try {
                queue.put(p=p.nextProbablePrime());
            } catch (InterruptedException e) {
                /**
                 *  阻塞队列响应中断并抛出异常后，可以在此根据业务需求进行必要的的收尾工作
                 */
            }
        }
    }

    /**
     *  调用中断方法触发阻塞队列的中断异常，从而使线程不至于无限时阻塞，能够响应中断并退出任务执行
     */
    public void cancel(){
        interrupt();
    }
}
