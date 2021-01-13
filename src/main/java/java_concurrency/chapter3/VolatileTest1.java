package java_concurrency.chapter3;

/**
 * @author chen
 * @date 2021/1/12 22:14
 * @Description 可见性测试
 */
public class VolatileTest1 {
    private static boolean ready;
    private static int number;

    private static class MyThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new Thread(new MyThread()).start();
        number = 100;
        ready = true;
    }

}
