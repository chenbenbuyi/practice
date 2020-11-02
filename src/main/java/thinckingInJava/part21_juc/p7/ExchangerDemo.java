package thinckingInJava.part21_juc.p7;

import java.util.concurrent.Exchanger;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/8/15 11:12
 * @Description 交换栅栏
 * Exchanger（交换者）是一个用于线程间协作的工具类。
 * Exchanger用于进行线程间的数据交换。它提供一个同步点，在这个同步点两个线程可以交换彼此的数据。这两个线程通过exchange方法交换数据， 如果第一个线程先执行exchange方法，它会一直等待第二个线程也执行exchange，当两个线程都到达同步点时，这两个线程就可以交换数据，将本线程生产出来的数据传递给对方。
 * 因此使用Exchanger的重点是成对的线程使用exchange()方法，当有一对线程达到了同步点，就会进行交换数据。因此该工具类的线程对象是成对的。
 */
public class ExchangerDemo {


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 20, TimeUnit.SECONDS, new SynchronousQueue<>());
        Exchanger<String> exchanger = new Exchanger<>();
        executor.execute(new ExchangerProceducer<>(exchanger));
        executor.execute(new ExchangerCustomer<>(exchanger));
        executor.shutdown();
    }
}


class ExchangerProceducer<T> implements Runnable {

    private String str = "我是生产者";

    private Exchanger<T> exchanger;

    public ExchangerProceducer(Exchanger<T> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            TimeUnit.NANOSECONDS.sleep(1000);
            T t = exchanger.exchange((T) str);
            System.out.println("生产者产出：" + t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class ExchangerCustomer<T> implements Runnable {
    private String str = "我是消费者";
    private Exchanger<T> exchanger;

    public ExchangerCustomer(Exchanger<T> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(5);
            T t = exchanger.exchange((T) str);
            System.out.println("消费者产出：" + t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}