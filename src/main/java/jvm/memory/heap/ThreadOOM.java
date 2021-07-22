package jvm.memory.heap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * JVM 堆内存溢出后，其他线程是否可继续工作？
 * 堆内存配置参数：-Xmx10m -Xms10m
 */
public class ThreadOOM {


    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        new Thread(() -> {
            List<byte[]> list = new ArrayList<>();
            while (true) {
                System.out.println("线程一正常运行使用------------");
                byte[] b = new byte[1024 * 1024 * 1];
                list.add(b);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"线程二").start();

        // 线程二
        new Thread(() -> {
            while (true) {
                System.out.println("线程二正常运行使用------------");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"线程二").start();
    }
}