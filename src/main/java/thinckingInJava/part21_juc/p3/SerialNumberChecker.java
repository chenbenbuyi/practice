package thinckingInJava.part21_juc.p3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/5/17 8:29
 * @Description
 */

public class SerialNumberChecker {
    private static final int SIZE = 10;
    private static CircularSet circularSet = new CircularSet(1000);
    private static ExecutorService exec = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 30L, TimeUnit.SECONDS, new SynchronousQueue<>());

    static class SerialChecker implements Runnable {
        public void run() {
            while (true) {
                int serial = SerialNumberGenerator.nextSerialNumber();
                if (circularSet.contains(serial)) {
                    System.out.println("重复的序列号：" + serial);
                    System.exit(0);
                }
                circularSet.add(serial);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new SerialChecker());
        }
    }
}

class CircularSet {

    private int[] array;

    private int lenth;

    private int index = 0;

    CircularSet(int size) {
        array = new int[size];
        lenth = size;
        for (int i = 0; i < size; i++) {
            array[i] = -1;
        }
    }

    public synchronized void add(int i) {
        array[index] = i;
        index = ++index % lenth;
        System.out.println("i的值为："+i+" index = "+index);
    }

    public synchronized boolean contains(int val) {
        for (int i = 0; i < lenth; i++) {
            if (array[i] == val)
                return true;
        }
        return false;
    }


}


