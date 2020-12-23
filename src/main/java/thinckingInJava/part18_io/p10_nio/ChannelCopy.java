package thinckingInJava.part18_io.p10_nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author chen
 * @date 2020/12/22 23:00
 */
public class ChannelCopy {
    private static final int BSIZE = 1024;
    private static final String PATH = "D:\\test";

    public static void main(String[] args) throws IOException {
        FileChannel in = new FileInputStream(args[0]).getChannel();
        FileChannel out = new FileOutputStream(args[1]).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        while (in.read(buffer)!=-1){
            /** 这里的流程逻辑为：
             *  每次read操作，都会将数据读取到缓冲器，调用 flip 方法相当于是一种准备，以便其内部信息可以被write提取
             *  write操作后，信息仍然在缓冲器内，调用clear重新安排内部指针，以便缓冲器在另一个read操作之前能做好数据接收准备
             *  实际上，clear并不会清除缓冲区的数据
             *
             */
            buffer.flip();
            out.write(buffer);
            buffer.clear();
        }
    }
}
