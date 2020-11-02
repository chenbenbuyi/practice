package thinckingInJava.part21_juc.p7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author chen
 * @date 2020/7/27 22:26
 * @Description 可循环使用（Cyclic）的屏障（Barrier）
 */
public class HorseRace {

    private static final int FINISH_LINE = 1;
    private List<Horse> horses = new ArrayList<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public HorseRace(int nHorses, final int pause) {
        // 表示nHorses的值递减为0，才执行Runnable线程任务
        /** public CyclicBarrier(int parties, Runnable barrierAction)
         * parties 屏障跳闸前必须调用 await()，以使计数器递减
         * barrierAction 屏障跳闸时执行的任务，由最后一个进入屏障的线程执行。如果没有动作则设置为null
         */
        CyclicBarrier barrier = new CyclicBarrier(nHorses, () -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < FINISH_LINE; i++) {
                stringBuilder.append("=");
            }
            System.out.println("终点线：" + stringBuilder);
            for (Horse hors : horses) {
                System.out.println(hors.tracks());
            }
            for (Horse hors : horses) {
                if (hors.getStrides() >= FINISH_LINE) {
                    System.out.println(hors + "赢得比赛");
                    executorService.shutdownNow();
                    return;
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(pause);
            } catch (InterruptedException e) {
                System.out.println("barrier 线程中断");
            }
        });
        for (int i = 0; i < nHorses; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            executorService.execute(horse);
        }
    }

    public static void main(String[] args) {
        int nHorses = 7;
        int pause = 200;
        new HorseRace(nHorses, pause);

    }
}

class Horse implements Runnable {

    private static int counter = 0;
    // 每个对象的id是递增的
    private final int id = counter++;
    private int strides = 0;
    private static Random rand = new Random(47);
    private static CyclicBarrier barrier;

    public Horse(CyclicBarrier b) {
        barrier = b;
    }

    public synchronized int getStrides() {
        return strides;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    // 模拟每匹马每次跑动的步幅 0 1 2
                    strides = rand.nextInt(3);
                }
                barrier.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Horse{" +
                "id=" + id +
                '}';
    }

    public String tracks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getStrides(); i++) {
            stringBuilder.append("*");
        }
        stringBuilder.append(id);
        return stringBuilder.toString();
    }
}
