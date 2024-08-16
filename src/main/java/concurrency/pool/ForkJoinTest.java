package concurrency.pool;

import cn.hutool.core.date.TimeInterval;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chen
 * @date 2022/12/27 22:13
 * @Description Fork/Join 测验
 * <p>
 * Fork/Join 主要用来支持分治任务模型的并行计算，fork对应任务分解，join 对应结果合并。
 * Fork/Join 计算框架也主要包含两部分，一部分是分治任务线程池 {@link ForkJoinPool} ，一部分是分治任务 {@link java.util.concurrent.ForkJoinTask} ，类似于 ThreadPoolExecutor 和 Runnable 的关系
 * ForkJoinTask 作为抽象类，有两个很重要的通过递归方式处理分治任务的子类：{@link RecursiveTask} 和 {@link RecursiveAction} 。区别在于一个有返回值，一个没有返回值
 * <p>
 * Fork/Join 用到的分治线程池 ForkJoinPool 本质上也是生产者和消费者模式实现，与线程池内部只有一个共享任务队列不同，ForkJoinPool 内部有多个任务队列，当通过其 invoke或submit方法提交任务时，其内部会根据一定的路由规则将任务提交到一个任务队列；而如果任务执行过程中创建了子任务，子任务也会提交到工作线程对应的任务队列中
 * fork 和join 这一对任务拆分和结果合并的方法使用了一系列内部的、从属于每个线程的队列来操作任务，并将线程从执行一个任务切换到执行另外的任务
 * <p>
 * 其它一些学习参考：
 * https://www.liaoxuefeng.com/article/1146802219354112
 */

@Slf4j
public class ForkJoinTest {

    private double[] doubles;


    /**
     * 计算数组中小于 0.5 的元素个数
     * 源自《Java性能权威指南》 9.2 示例代码
     */

    private class ForkJoinTask extends RecursiveTask<Integer> {

        private static final long serialVersionUID = -6210851159340377932L;
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
                 */
                int mid = (first + last) >>> 1;
                /**
                 * 上面的表达式其实是除 2 的逼格写法。Java算法中的 >>>1 与 /2 是等效的，前者运算效率高，后者运算级别高
                 * Java中的移位运算符，有三种:
                 *  左移运算符，num << 1，相当于num乘以2
                 *  右移运算符，num >> 1，相当于num除以2
                 *  无符号右移，num >>> 1，相当于num除以2，忽略符号位，空位都以0补齐，也就意味着最高位的符号位都会以 0 来补齐。
                 *  因此，无符号右移这种情况，对非负数和右移运算时等效的，但是计算负数就会谬以千里。
                 *
                 */
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

    /**
     * 计算数组中小于 0.5 的元素个数（线程池版本）
     * 源自《Java性能权威指南》 9.2 示例比较代码
     * 思路：先将数组按照长度进行分段
     */
    private class ThreadPoolExecutorTask implements Callable<Integer> {
        private int first;
        private int end;

        public ThreadPoolExecutorTask(int first, int end) {
            this.first = first;
            this.end = end;
        }

        @Override
        public Integer call() throws Exception {
            int subCount = 0;
            for (int i = first; i <= end; i++) {
                if (doubles[i] < 0.5) {
                    subCount++;
                }
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
     * 源自借极客时间《Java 并发编程实战》 第 26 讲案例
     */
    static class Fibonacci extends RecursiveTask<Integer> {
        private static final long serialVersionUID = -6184685958922959004L;
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


    /**
     * Fork/Join 和线程池的方式处理同样的任务比较测试
     * <p>
     * 通常情况下，使用 Fork/Join 并不一定比线程池提升性能，实际在合理利用线程的情况下，线程池的性能可能会更好，因为Fork/Join需要不断的分解任务、合并任务，有额外的消耗。
     * Fork/Join 的优势主要在于任务分解，将不便于线程池直接执行的大任务通过递归的方式不断分解成N多子任务进行处理，从而实现较少的线程数执行大量的任务。如果同样的任务数交由线程池来实现，则需要不切实际的线程数量
     * 此外， Fork/Join 还有个额外的特性就是"任务窃取"机制，内部的工作线程如果执行完自己队列中的任务有空闲，会从其他线程的工作队列中"窃取"任务执行，从而最大化利用线程资源，而线程池就做不到这点。
     * 所以，一些按照线程池方案分解的任务案例中，如果某线程执行的任务耗时尤其长，其它线程执行完后只有等待；但是Fork/Join 的任务窃取却可以让所有线程都保持忙碌，这种情况相比线程池方案会有明显的性能提升。
     * 通常而言，任务耗时如果是均衡的，使用线程池能获得更好的性能，但如果任务有不均衡性，使用Fork/Join 能获得更好的性能（注意任务分解的结束条件，这也会影响最终的性能）
     */
    @Test
    public void testDoubleNum() throws Exception {
        log.info("");
        int length = 10000000;
        doubles = createArrayOfRandomDoubles(length);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        int n = forkJoinPool.invoke(new ForkJoinTask(0, length - 1));
        log.info("通过Fork/Join发现{}个小于0.5的元素值！", n);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, Long.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        // 注意因为类型擦除，Java 无法直接创建泛型对象的数组
        Future<Integer>[] f = (Future<Integer>[]) Array.newInstance(Future.class, 4);
        int size = doubles.length / 4;
        for (int i = 0; i < 3; i++) {
            f[i] = executor.submit(new ThreadPoolExecutorTask(i * size, (i + 1) * size - 1));
        }

        f[3] = executor.submit(new ThreadPoolExecutorTask(3 * size, doubles.length - 1));

        int nt = 0;
        for (int i = 0; i < 4; i++) {
            nt += f[i].get();
        }
        executor.shutdown();
        log.info("通过ThreadPoolExecutor的方式发现{}个小于0.5的元素值！", nt);

    }

    /**
     * Java 1.8 中并行流是以ForkJoinPool为基础的，默认情况下，所有的并行流计算，都共享一个ForkJoinPool ，其大小默认为目标机器处理器数量
     * 以下为验证测试。从测验的任务线程名称看，两次看似不相干的并行任务执行，其内部任务执行线程其实是共享的。
     * 所以，如果这种共享情况下有耗时的IO操作，可能会拖慢整个系统的性能
     */
    @Test
    public void StreamTest() throws InterruptedException {
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
        executor.shutdown();
        System.out.println("执行完毕。。。。。。。。");
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

    private static AtomicLong counter = new AtomicLong(0L);


    /**
     *  通过对比测试，在这种业务场景下，使用forkjoin比传统的递归速度有明显的提升
     */
    @SneakyThrows
    @Test
    public void testFile(){

        String filePath = "C:\\Software\\Working";

        TimeInterval timeInterval = new TimeInterval();
        fileRecursion(new File(filePath));
        System.out.println("普通递归方式花费:" + timeInterval.intervalRestart() / 1000.0 + "秒，总文件数量：" + counter.get());

        System.out.println("---------------------------分割线-------------------------");
        counter.set(0);

        ForkJoinPool forkjoinPool = new ForkJoinPool(6);
        File file = new File(filePath);
        FileForkJoinAction action = new FileForkJoinAction(file);
        forkjoinPool.invoke(action);
        // 停止接收任务
        forkjoinPool.shutdown();
        //等待任务执行结束
        forkjoinPool.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println("ForkJoin优化方式花费:" + timeInterval.intervalRestart() / 1000.0 +  "秒，总文件数量：" + counter.get());

        System.out.println("---------------------------分割线-------------------------");
        counter.set(0);

        forkjoinPool = new ForkJoinPool(6);
        FileForkJoinTask task = new FileForkJoinTask(file);
        List<String> list = forkjoinPool.invoke(task);
        //阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束
        forkjoinPool.awaitTermination(2, TimeUnit.SECONDS);
        // 关闭线程池
        forkjoinPool.shutdown();
        // todo 缓存中不足1000条的剩余数据处理
        counter.addAndGet(list.size());

        System.out.println("ForkJoin优化方式花费:" + timeInterval.intervalRestart() / 1000.0 +  "秒，总文件数量：" + counter.get());
    }

    /**
     * 递归遍历文件统计指定目录下的总文件数量
     * 获取到C:\Software\Working目录下 122981 个文件总花费 35s
     */
    private void fileRecursion(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null)
                    for (File son : files) {
                        fileRecursion(son);
                    }
            } else {
                counter.incrementAndGet();
            }
        }
    }


    private class FileForkJoinAction extends RecursiveAction {
        private static final long serialVersionUID = -7560534663117090540L;
        private File path; // 当前要搜索的目录
        public FileForkJoinAction(File path) {
            this.path = path;
        }
        @Override
        protected void compute() {
            try {
                // 定义一个文件目录集合
                List<FileForkJoinAction> subTasks = new ArrayList<>();
                // 根据当前要搜索目录的，找到所有的文件
                File[] files = path.listFiles();
                // 判断是否为空，不为空则继续往下搜索
                if (null != files) {
                    for (File file : files) {
                        // 判断是否是目录
                        if (file.isDirectory()) {
                            subTasks.add(new FileForkJoinAction(file));
                        } else {
                            counter.incrementAndGet();
                        }
                    }
                    if (!subTasks.isEmpty()) {
                        for (FileForkJoinAction subTask : invokeAll(subTasks)) {
                            // 等待子任务完成
                            subTask.join();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class FileForkJoinTask extends RecursiveTask<List<String>> {
        private static final long serialVersionUID = 8646384229832296891L;

        private File file;
        public FileForkJoinTask(File file) {
            this.file = file;
        }

        @Override
        protected List<String> compute() {
            List<String> buffer = new ArrayList<>();
            if (file != null) {
                if (file.isDirectory()) {
                    File[] sons = file.listFiles();
                    if (sons != null && sons.length > 0) {
                        for (File son : sons) {
                            FileForkJoinTask task = new FileForkJoinTask(son);
                            invokeAll(task);
                            List<String> join = task.join();
                            buffer.addAll(join);
                            if (buffer.size() > 1000) {
                                // todo 缓存批处理, 拿到指定批量的值做诸如存库等其它业务逻辑处理
                                counter.addAndGet(buffer.size());
                                buffer.clear();
                            }
                        }
                    }
                } else {
                    buffer.add(file.getAbsolutePath());
                }
            }
            return buffer;
        }
    }


}
