package jvm.memory.stack;

import org.apache.commons.lang3.time.StopWatch;
import thinckingInJava.comm.model.User;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/5/15 15:45
 * @Description 逃逸分析——栈上分配
 * 测试参数： -Xmx200m -Xms200m -XX:-DoEscapeAnalysis -XX:+PrintGCDetails
 * 参数说明：DoEscapeAnalysis 逃逸分析，前面的 + 或 - 分别表示开启或关闭 在 Server VM 模式下，默认值是开启逃逸分析的
 * Java虚拟机规范描述：
 *  所有对象实例和数组都要在堆上分配，但随着JIT编译器的发展和逃逸分析技术的成熟，栈上分配，标量替换优化技术将会导致一些微妙的变化——就是说所有对象都在堆上进行分配并不是那么绝对的！
 * 测试效果：
 *  在关闭逃逸分析的情况下，创建完1千万示例耗时在90ms左右，并且会触发几次新生代的Minor GC（如果自己堆设置过大，也不会触发），通过VisualVM 抽样器内存堆柱图中查看到User实例个数在经理过GC之后仍然达到百万级别
 *  开启逃逸分析的情况下,没有GC日志产生，说明没有触发垃圾回收，创建完1千万示例耗时在8ms左右，时间大大缩短,而且通过VisualVM 抽样器内存堆柱图中查看到User实例对象数只有十来万个
 *
 *  测试可能会产生疑惑：为什么开启逃逸分析后，在堆上还是会有相当数量的对象实例存在呢？
 *  有一种说法，逃逸分析理论上可行，但技术尚不成熟，所以Oracle HotSpot虚拟机并没有实现通过逃逸分析来实现对象的栈上分配，本示例中对象实例的减少只是因为开启逃逸分析后，JIT编译器通过标量替换优化后的效果
 *  所以，现阶段可以明确一点：所有对象，仍然还是在堆上分配的
 *  ps:关于
 */
public class StackAllocationTest {
    final static StopWatch stopWatch = new StopWatch();

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(20);
        stopWatch.start();
        for (int i = 0; i < 10000000; i++) {
            allocat();
        }
        System.out.println("总时间消耗："+stopWatch.getTime(TimeUnit.MILLISECONDS)+"ms");

        TimeUnit.SECONDS.sleep(6000);
    }

    // 该方法内创建的对象并未溢出
    private static void allocat() {
        User user = new User();
    }


}
