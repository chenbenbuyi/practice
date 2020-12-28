package thinckingInJava.part18_io.p10_nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author chen
 * @date 2020/12/22 0:08
 * @Description 获取通道
 *  新的I/O 通过使用更接近于操作系统执行I/O的结构（通道和缓冲器）来提高文件和网络I/O 的速度，
 *  实际的I/O过程中，并不会直接和通道打交道，而是和缓冲器（ByteBuffer——唯一与通道进行交互的缓冲器）进行交互，由缓冲器去通道获取数据或者向通道写入数据
 */
public class GetChannel {
    private static final int BSIZE = 1024;
    private static final String PATH = "D:\\test";

    public static void main(String[] args) throws IOException {
        FileChannel fileChannel = new FileOutputStream(PATH).getChannel();
        // 追加写入
        fileChannel.write(ByteBuffer.wrap("hello".getBytes()));
        fileChannel.close();
        fileChannel = new RandomAccessFile(PATH, "rw").getChannel();
        //定位到最后位置
        fileChannel.position(fileChannel.size());
        fileChannel.write(ByteBuffer.wrap("word".getBytes()));
        fileChannel.close();
        fileChannel = new FileInputStream(PATH).getChannel();
        /**
         *  allocate 分配一个新的指定字节数容量的字节缓冲区
         *  nio 的目标就是快速移动大量数据，ByteBuffer 的大小尤其重要
         */
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        fileChannel.read(buffer);
        // flip 翻转缓冲区
        buffer.flip();
        while (buffer.hasRemaining()){
            System.out.print((char) buffer.get());
        }
    }
}
