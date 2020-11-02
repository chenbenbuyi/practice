package thinckingInJava.part21_juc.p5;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/17 23:53
 * @Description 利用线程间通信编写生产者消费者示例
 */
public class MyProducerAndConsumerTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 20, TimeUnit.SECONDS, new SynchronousQueue<>());
        MyTask task = new MyTask();
        executor.execute(new Producer(task));
        executor.execute(new Consumer(task));
        TimeUnit.SECONDS.sleep(2);
        // 都shutdown了程序还没停?
        executor.shutdown();
    }
}


class MyTask {
    public boolean hasShangcai = true;

    public synchronized void chaocai() {
        System.out.println("服务员上菜完毕，开始炒菜");
        hasShangcai = false;
        notify();
    }

    public synchronized void shangcai() {
        System.out.println("厨师炒菜完毕，服务员开始上菜");
        hasShangcai = true;
        notify();
    }


    public synchronized void waitForShangcai() throws InterruptedException {
        wait();
    }

    public synchronized void waitForchaocai() throws InterruptedException {
        wait();
    }

}

class Producer implements Runnable {
    MyTask task;

    public Producer(MyTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            System.out.println("线程执行中，，，，");
            if (!task.hasShangcai) {
                try {
                    task.waitForShangcai();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                task.chaocai();
            }
        }
    }
}

class Consumer implements Runnable {

    MyTask task;

    public Consumer(MyTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (task.hasShangcai) {
                try {
                    task.waitForchaocai();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                task.shangcai();
            }
        }
    }
}
