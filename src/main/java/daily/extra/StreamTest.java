package daily.extra;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2022/12/26 22:46
 * @Description 学习中遇到以下说明：Java 1.8 中并行流是以ForkJoinPool为基础的，默认情况下，所有的并行流计算，都共享一个ForkJoinPool
 * 以下为验证测试。从测验的任务线程名称看，两次看似不相干的并行任务执行，其内部任务执行线程其实是共享的
 * // todo 通过服务访问进行测试
 */
public class StreamTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 100, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);


        CountDownLatch countDownLatch = new CountDownLatch(2);
        executor.execute(() -> {
            list.parallelStream().forEach(e -> {
                System.out.println(e + "当前线程：" + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            });
            countDownLatch.countDown();
        });

        System.out.println("=============================");


        executor.execute(() -> {
            list2.parallelStream().forEach(e -> {
                System.out.println(e + "当前线程2：" + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            });
            countDownLatch.countDown();
        });
        countDownLatch.await();

        System.out.println("执行完毕。。。。。。。。");

    }
}
