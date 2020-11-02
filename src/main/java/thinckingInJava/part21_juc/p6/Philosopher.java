package thinckingInJava.part21_juc.p6;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/25 21:15
 * @Description
 */
public class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;
    private final int id;
    private final int ponderFactor;
    private Random rand = new Random(50);

    public Philosopher(Chopstick left, Chopstick right, int id, int ponderFactor) {
        this.left = left;
        this.right = right;
        this.id = id;
        this.ponderFactor = ponderFactor;
    }

    private void pause() throws InterruptedException {
        // 值为非0则会休眠一段时间，0
        if (ponderFactor == 0) return;
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println(this + "正在思考");
                pause();
                System.out.println(this+"拿起右边筷子");
                this.right.take();
                System.out.println(this+"拿起左边筷子");
                this.left.take();
                System.out.println(this+"正在用餐...");
                pause();
                right.drop();
                left.drop();
            }
        } catch (InterruptedException e) {
            System.out.println(this+"中断退出线程");
        }
    }

    @Override
    public String toString() {
        return "Philosopher{" +
                "id=" + id +
                '}';
    }
}
