package daily.extra;

import org.junit.Test;

/**
 * @date: 2020/12/29 19:55
 * @author: chen
 * @desc: 日常单元测试集合
 */
public class UnitTest {
    /**
     * 测试内存占用  堆内存设置 -Xmx=
     * Too small initial heap
     * -Xmx 堆大小上限，最大空间(-Xmx)是物理内存的1/4，如果程序中分配的内存超过了这个限制，那么会出现
     * 注意：内存撑满是很危险的事情，轻则电脑短时间卡死，重则电脑
     */

    public static void main(String[] args) {
        System.out.println("free:" + Runtime.getRuntime().freeMemory() / 1024
                / 1024);
        System.out.println("total:" + Runtime.getRuntime().totalMemory() / 1024
                / 1024);
        System.out.println("max:" + Runtime.getRuntime().maxMemory() / 1024
                / 1024);
        System.out.println("=============");
        long t = System.currentTimeMillis();
        try {
            Thread.sleep(3000);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        String[] aaa = new String[2000000];
        System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024);
        System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);
        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);
        System.out.println("=============");
        try {
            Thread.sleep(3000);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        for (int i = 0; i < 2000000; i++) {
            aaa[i] = new String("aaa");
        }
        System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024);
        System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);
        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);
        System.out.println("=============");
        try {
            Thread.sleep(30000);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
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
