package thinckingInJava.part18_io.p10_nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author chen
 * @date 2020/12/26 23:24
 * @Description
 */
public class TransferTo {
    public static void main(String[] args) throws IOException {
        FileChannel in = new FileInputStream("D:\\test").getChannel(),
                out = new FileOutputStream("D:\\test2").getChannel();
        /**
         *  从源通道读取 count 个字节，并将其写入此通道文件，从指定的位置（position）开始
         */
        in.transferFrom(in, 0, in.size());

    }
}
