package thinckingInJava.part21_juc.p3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chen
 * @Description
 */
public abstract class IntGenerator {

    private volatile boolean canceled = false;

    public abstract int next();

    public void cacel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }

    static class MyIntGenertor extends IntGenerator {
        private int currentEventValue = 0;

        @Override
//        public  int next() {
        public synchronized int next() {
            System.out.println("线程 " + Thread.currentThread().getName() + " 进入该方法，此时的变量值：" + currentEventValue);
            currentEventValue++;
            // 当一个线程执行到这里的时候，选择礼让，这个时候，currentEventValue的值就可能（并发数量大的时候，是一定会）出现不一致的状态
//            Thread.yield();   // cause failure faster
            currentEventValue++;
            System.out.println("线程 " + Thread.currentThread().getName() + " 退出该方法，此时的变量值：" + currentEventValue);
            return currentEventValue;
        }
    }


    // 显示的锁对象 Lock
    static class LockIntGenertor extends IntGenerator {
        private int currentEventValue = 0;
        Lock lock = new ReentrantLock(); // 要在方法返回值返回之后才释放锁，所以，最好是用在finally语句块中
        @Override
        public int next() {
            try {
                lock.lock();
                System.out.println("线程 " + Thread.currentThread().getName() + " 进入方法，此时的变量值：" + currentEventValue);
                currentEventValue++;
                Thread.yield();
                currentEventValue++;
                System.out.println("线程 " + Thread.currentThread().getName() + " 退出方法，此时的变量值：" + currentEventValue);
                return currentEventValue;
            } finally {
                lock.unlock();
            }
        }
    }


}
