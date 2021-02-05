package daily.extra;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @date: 2020/12/29 19:55
 * @author: chen
 * @desc: 日常单元测试集合
 */
@Slf4j
public class UnitTest {
    /**
     * 测试内存占用  堆内存设置 Too small initial heap
     * -Xmx 堆大小上限，最大空间(-Xmx)是物理内存的1/4，如果程序中分配的内存超过了这个限制，那么会出现
     * 注意：内存撑满是很危险的事情，轻则电脑短时间卡死，重则电脑
     */

    public static void main(String[] args) throws InterruptedException {
        log.info("虚拟机空闲内存:{}M", Runtime.getRuntime().freeMemory() / 1024/ 1024);
        log.info("JVM总内存大小:{}M" ,Runtime.getRuntime().totalMemory() / 1024/ 1024);
        log.info("JVM尝试使用的最大内存:{}M" + Runtime.getRuntime().maxMemory() / 1024/ 1024);
        System.out.println("=============");
        TimeUnit.SECONDS.sleep(3);
        // 申请较大内存
        String[] aaa = new String[2000000];
        System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024);
        System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);
        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);
        System.out.println("=============");
        TimeUnit.SECONDS.sleep(3);
        for (int i = 0; i < 2000000; i++) {
            aaa[i] = new String("aaa");
        }
        System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024);
        System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);
        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);
        System.out.println("=============");
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void test1() {
        //获取当前堆的大小 byte 单位
        long heapSize = Runtime.getRuntime().totalMemory();
        System.out.println((heapSize / 1024) / 1024 + "M");
        //获取当前堆的空闲 byte 单位
        long freeSize = Runtime.getRuntime().freeMemory();
        System.out.println((freeSize / 1024) / 1024 + "M");
        //获取堆的最大大小 byte单位
        long heapMaxSize = Runtime.getRuntime().maxMemory();
        System.out.println((heapMaxSize / 1024) / 1024 + "M");
        boolean[] booleans = new boolean[Integer.MAX_VALUE - 2];
        long heapSize2 = Runtime.getRuntime().totalMemory();
        System.out.println((heapSize2 / 1024) / 1024 + "M");
        //获取当前堆的空闲 byte 单位
        long freeSize2 = Runtime.getRuntime().freeMemory();
        System.out.println((freeSize2 / 1024) / 1024 + "M");
        //获取堆的最大大小 byte单位
        long heapMaxSize2 = Runtime.getRuntime().maxMemory();
        System.out.println((heapMaxSize2 / 1024) / 1024 + "M");
//        int[] ints = new int[Integer.MAX_VALUE-2];
    }
}
