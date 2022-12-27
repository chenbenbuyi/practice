package concurrency.pool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * @author chen
 * @date 2022/12/27 22:13
 * @Description 利用 Fork/Join 测验
 * <p>
 * Fork/Join 主要用来支持分治任务模型的并行计算，fork对应任务分解，join 对应结果合并。
 * Fork/Join 计算框架也主要包含两部分，一部分是分支任务线程池 {@link ForkJoinPool} ，一部分是分治任务 {@link java.util.concurrent.ForkJoinTask} ，类似于 ThreadPoolExecutor 和 Runnable 的关系
 * ForkJoinTask 作为抽象类，有两个很重要的通过递归方式处理分治任务的子类：{@link RecursiveTask} 和 {@link RecursiveAction} 。区别在于一个有返回值，一个没有返回值
 */

@Slf4j
public class ForkJoinTest {

    private static double[] doubles;


    /**
     * 计算数组中小于 0.5 的元素个数
     * 源自《Java性能权威指南》 9.2 示例代码
     */

    private static class ForkJoinTask extends RecursiveTask<Integer> {

        private int first;
        private int last;

        public ForkJoinTask(int first, int last) {
            this.first = first;
            this.last = last;
        }

        @Override
        protected Integer compute() {
            int subCount;

            if (last - first < 10) {
                subCount = 0;
                for (int i = first; i <= last; i++) {
                    if (doubles[i] < 0.5)
                        subCount++;
                }
            } else {
                /**
                 * 注意ForkJoinTask中关键的 fork 和 join 方法
                 * fork 方法会异步的执行一个子任务
                 * join 方法会阻塞当前线程以等待子任务执行结果
                 *
                 */
                int mid = (first + last) >>> 1;
                ForkJoinTask left = new ForkJoinTask(first, mid);
                left.fork();
                ForkJoinTask right = new ForkJoinTask(mid + 1, last);
                right.fork();
                subCount = left.join();
                subCount += right.join();

            }

            return subCount;
        }

    }


    private static double[] createArrayOfRandomDoubles(int length) {
        double min = 0.01;
        double max = 10.0;
        double[] doubles = new double[length];
        for (int i = 0; i < length; i++) {
            double nextDouble = RandomUtils.nextDouble(min, max);
            doubles[i] = nextDouble;
        }

        return doubles;
    }


    /**
     * 通过 Fork/Join 计算斐波那契数列
     */
    static class Fibonacci extends RecursiveTask<Integer> {
        final int n;

        Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1)
                return n;
            Fibonacci f1 = new Fibonacci(n - 1);
            //创建子任务
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            //等待子任务结果，并合并结果
            return f2.compute() + f1.join();
        }
    }

    @Test
    public void testDoubleNum() {
        int length = 1000000;
        doubles = createArrayOfRandomDoubles(length);
        int n = new ForkJoinPool().invoke(new ForkJoinTask(0, length - 1));
        log.info("发现{}个小于0.5的元素值！", n);
    }


    @Test
    public void testFibonacci() {
        //创建分治任务线程池
        ForkJoinPool fjp = new ForkJoinPool(4);
        //创建分治任务
        Fibonacci fib = new Fibonacci(30);
        //调用invoke方法 启动分治任务
        Integer result = fjp.invoke(fib);
        //输出结果
        log.info("执行结果：{}", result);
    }

}
