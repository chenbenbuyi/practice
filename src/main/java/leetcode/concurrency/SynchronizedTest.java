package leetcode.concurrency;

import java.util.concurrent.TimeUnit;

public class SynchronizedTest {

    public static void main(String[] args) {
        Thread why = new Thread(new TicketConsumer(10), "陈大仙");
        Thread mx = new Thread(new TicketConsumer(10), "陈小远");
//        why.start();
//        mx.start();
    }
}

/**
 *  两个问题：
 *      1.为什么 synchronized 没有生效？
 *      2.为什么锁对象 System.identityHashCode 的输出是一样的？
 *
 *    参考链接：https://www.cnblogs.com/thisiswhy/p/15892044.html
 */
class TicketConsumer implements Runnable {

    private volatile static Integer ticket;

    public TicketConsumer(int ticket) {
        this.ticket = ticket;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + "开始抢第" + ticket + "张票，对象加锁之前：" + System.identityHashCode(ticket));
            synchronized (ticket) {
                System.out.println(Thread.currentThread().getName() + "抢到第" + ticket + "张票，成功锁到的对象：" + System.identityHashCode(ticket));
                if (ticket > 0) {
                    try {
                        //模拟抢票延迟
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "抢到了第" + ticket-- + "张票，票数减一");
                } else {
                    return;
                }
            }
        }
    }
}