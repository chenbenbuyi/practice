package thinckingInJava.part21_juc.p3;

/**
 * @author chen
 * @date 2020/6/3 7:36
 * @Description 同步必须确保在同一个对象上，否则达不到统同步效果
 */
public class SyncObject {
    private Object object = new Object();

    private synchronized void fun1() {
        for (int i = 0; i < 5; i++) {
            System.out.println("我是fun1");
            Thread.yield();
        }
    }

    private void fun2() {
        synchronized (object) {
            for (int i = 0; i < 5; i++) {
                System.out.println("我是fun2");
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        SyncObject syncObject = new SyncObject();
        new Thread(() -> syncObject.fun1()).start();
        syncObject.fun2();
    }
}
