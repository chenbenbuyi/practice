package thinckingInJava.part21_juc.other;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/23 23:32
 * @Description 同步队列测试
 *  SynchronousQueue 没有实际容量，只是做为元素交接，单独的放入和获取都会产生阻塞：
 *      想要放入元素，必须要有线程在获取；想要获取元素，必须要有线程在放入
 */
public class SynchronousQueueTest {
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        new Thread(()->{
            System.out.println("放入队列");
            try {
                TimeUnit.SECONDS.sleep(4);
                synchronousQueue.put("sd");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
//        String take = synchronousQueue.take();
//        System.out.println("获取元素："+take);
    }
}
