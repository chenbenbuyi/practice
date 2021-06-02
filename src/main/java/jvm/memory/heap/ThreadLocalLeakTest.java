package jvm.memory.heap;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/6/2 22:17
 * @Description 探究ThreadLocal的内存泄漏问题
 */
public class ThreadLocalLeakTest {
    private static final Integer SIZE = 500;

    static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

    static ThreadLocal<O> local = new ThreadLocal<>();


    public static void main(String[] args) {
        try {
            // 打开工具的时间
            TimeUnit.SECONDS.sleep(5);
            for (int i = 0; i < SIZE; i++) {
                executor.execute(() -> {
                    local.set(new O());
                    System.out.println("开始执行咯");
                    /**
                     *  通过内存工具查看，该行代码对内存的影响，验证内存泄漏问题
                     */
                    local.remove();
                });
                TimeUnit.MILLISECONDS.sleep(100);
            }
            local = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static class O {
        protected byte[] mem = new byte[1024 * 1024 * 5];
    }


}
