package thinckingInJava.part21_juc.p4;

/**
 * @author chen
 * @date 2020/7/2 7:36
 * @Description 证明同一个线程任务可以多次获得相同对象的锁
 */
public class MultiLock {

    public synchronized void f1(int count) {
        if (count-- > 0) {
            System.out.println("f2 调用 f1的count值为：" + count);
            f2(count);
        }
    }

    public synchronized void f2(int count) {
        if (count-- > 0) {
            System.out.println("f1 调用 f2的count值为：" + count);
            f1(count);
        }
    }

    public static void main(String[] args) {
        MultiLock multiLock = new MultiLock();
        new Thread(() -> {
            multiLock.f1(10);
        }).start();
    }
}
