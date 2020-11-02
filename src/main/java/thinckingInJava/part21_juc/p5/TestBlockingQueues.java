package thinckingInJava.part21_juc.p5;

import thinckingInJava.part21_juc.p2.LiftOff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/21 23:39
 * @Description 阻塞队列
 */
public class TestBlockingQueues {
    static void getKey() {
        try {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void getKey(String msg) {
        System.out.println(msg);
        getKey();
    }

    static void test(String msg, BlockingQueue<LiftOff> queue) {
        System.out.println("开始构造："+msg);
        // 在静态的方法中不能使用内部类,所以改为静态即可
        final LiftOffRunner runner = new LiftOffRunner(queue);
        Thread thread = new Thread(runner);
        thread.start();
        // 上面为开启线程从队列中获取元素；下面则通过循环向队列中添加元素
        for (int i = 0; i < 5; i++) {
            runner.add(new LiftOff(5));
            System.out.println("循环添加元素？？？？？？？");
        }
        getKey("循环结束，press enter " + msg);
        thread.interrupt();
        System.out.println("完成" + msg + "测试");
    }

    public static void main(String[] args) {
        // LinkedBlockingQueue 无界队列
//        test("LinkedBlockingQueue", new LinkedBlockingQueue<>());
        // ArrayBlockingQueue 固定尺寸的队列，构造时需要指定尺寸
        test("ArrayBlockingQueue", new ArrayBlockingQueue<>(1));
        // 没有实际容量的阻塞队列
        test("SynchronousQueue", new SynchronousQueue<>());
    }

    /**
     * 在静态的方法中不能使用内部类
     * 原理;因为内部类可以直接使用外部类的成员变量的，而成员变量是对象所属，
     * 只有对象创建出来了才可以使用成员变量，而在静态方法中可以不用创建对象就可以被调用,所以二者矛盾
     */
    static class LiftOffRunner implements Runnable {

        private BlockingQueue<LiftOff> rockets;

        public LiftOffRunner(BlockingQueue<LiftOff> rockets) {
            this.rockets = rockets;
        }

        public void add(LiftOff lo) {
            try {
                rockets.put(lo);
            } catch (InterruptedException e) {
                System.out.println("在放入队列的时候中断");
            }
        }


        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    TimeUnit.SECONDS.sleep(4);
//                    System.out.println("线程正在循环从头队列中取数据。。。");
//                    LiftOff take = rockets.take();
//                    System.out.println("获取到元素take: "+take);
//                    take.run();// 运行线程
                }
            } catch (InterruptedException e) {
                System.out.println("waking from take");
            }
            System.out.println("退出火箭发射");
        }
    }
}
