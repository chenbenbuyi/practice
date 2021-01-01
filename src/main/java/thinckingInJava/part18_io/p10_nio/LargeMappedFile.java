package thinckingInJava.part18_io.p10_nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author chen
 * @date 2020/12/30 23:02
 * @Description 内存映射文件
 * 内存映射文件允许我们创建和修改那些因为太大而无法入内存的文件.
 * 有了内存映射文件，我们可以假设整个文件都放在内存中，将其当成大数组来进行访问
 */
public class LargeMappedFile {

    static int lenth = 0x8FFFFFF;

    public static void main(String[] args) throws Exception {
        /**
         *  指定映射文件初始位置和映射区域长度
         */
        MappedByteBuffer out = new RandomAccessFile("D:\\test", "rw").getChannel().map(FileChannel.MapMode.READ_WRITE, 0, lenth);
        for (int i = 0; i < lenth; i++) {
            System.out.println(i);
            out.put((byte) 'x');
        }
        System.out.println("结束写入");
        for (int i = lenth / 2; i < lenth / 2 + 6; i++) {
            System.out.print((char) out.get(i));
        }
    }
}
