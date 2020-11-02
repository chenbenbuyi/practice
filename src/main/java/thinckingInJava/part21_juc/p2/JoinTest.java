package thinckingInJava.part21_juc.p2;

/**
 * @author chen
 * @date 2020/4/7 22:09
 * 某个线程在另一个线程上调用join,此线程将被挂起，直到目标线程任务结束
 * 比如在线程B中调用了线程A的Join()方法，直到线程A执行完毕后，才会继续执行线程B。
 * 当然，还可以指定join的超时时间，单位毫秒
 */
public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Sleeper sleeper = new Sleeper("sleeper 1", 1500),
                sleeper2 = new Sleeper("sleeper 2", 1600);
        new Joiner("joiner 1", sleeper);
        new Joiner("joiner 2", sleeper2);
        // 中断并不是强迫终止一个线程，它是一种协作机制，是给线程传递一个取消信号，但是由线程来决定如何以及何时退出。
        // 当对一个线程调用 interrupt() 时, 如果线程处于被阻塞状态（例如处于sleep, wait, join 等状态），那么线程将立即退出被阻塞状态，并抛出一个InterruptedException异常。
        // 如果线程处于正常活动状态，那么会将该线程的中断标志设置为 true，仅此而已。被设置中断标志的线程将继续正常运行，不受影响。
        sleeper2.interrupt();
        System.out.println("线程 sleeper 2 被中断");
    }

    static class Sleeper extends Thread {

        private int duration;

        // 不建议在构造器中开启线程，因为多线程的环境中，很有可能另一个线程会访问到不稳定的对象状态
        public Sleeper(String name, int sleepTime) {
            super(name);
            this.duration = sleepTime;
            start();
        }

        @Override
        public void run() {
            try {
                sleep(duration);
            } catch (InterruptedException e) {
                // 线程中断，将抛出InterruptedException异常，异常被捕获之后，中断标示将被清理，所以在catch子句的中断标示总是为false
                System.out.println("线程 " + getName() + " 中断，中断状态 Interrupted ：" + isInterrupted());
                return;
            }

            System.out.println("线程 " + getName() + " 任务执行完成");
        }
    }


    static class Joiner extends Thread {

        private Sleeper sleeper;

        // 不建议在构造器中开启线程，因为多线程的环境中，很有可能另一个线程会访问到不稳定的对象状态
        public Joiner(String name, Sleeper sleeper) {
            super(name);
            this.sleeper = sleeper;
            start();
        }

        @Override
        public void run() {
            try {
                sleeper.join();
            } catch (InterruptedException e) {
                System.out.println("线程 " + getName() + " 中断");
            }
            System.out.println("线程 " + getName() + " join 完成");
        }
    }
}
