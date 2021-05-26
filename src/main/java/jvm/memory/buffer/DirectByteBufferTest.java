package jvm.memory.buffer;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/5/20 21:27
 * @Description 直接内存测试
 *  在读写特别频繁的场合，使用直接内存能显著提高读写效率；
 *  Java 通过NIO库即可操作直接内存
 */
public class DirectByteBufferTest {
    // 直接内存字节数大小
    private static final int BUFFER_SIZE = 1024 * 1024 * 1024;

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        // 申请 1G 大小的直接内存
        System.out.println("1G的直接内存开始分配！");
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        System.out.println("分配完成1G的直接内存！");
        TimeUnit.SECONDS.sleep(10); // 延缓时间，便于在任务管理器中观察到内存大小变化
        System.out.println("1G的直接内存开始释放！");
        byteBuffer = null;
        System.gc();
        TimeUnit.SECONDS.sleep(10);

    }
}
