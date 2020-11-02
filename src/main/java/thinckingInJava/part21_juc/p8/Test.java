package thinckingInJava.part21_juc.p8;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author chen
 * @date 2020/8/31 22:12
 * @Description
 *
6 - Monitor Ctrl-Break
5 - Attach Listener
4 - Signal Dispatcher
3 - Finalizer
2 - Reference Handler
1 - main
 */
public class Test {

    public static void main(String[] args) {
        //构建 ThreadMXBean  Java 虚拟机中线程系统的管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //获取所有存活的线程信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            //打印出线程id 以及 线程name
            System.out.println(threadInfo.getThreadId() + " - " + threadInfo.getThreadName());
        }
    }
}
