package thinckingInJava.part18_io.p8;

import org.apache.commons.lang3.CharSet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @author chen
 * @date 2020/12/27 23:16
 * @Description
 */
public class BufferToText {
    private static final int BSIZE=2014;

    public static void main(String[] args) throws IOException {
        FileChannel fc  = new FileOutputStream("D:\\test").getChannel();
        fc.write(ByteBuffer.wrap("一些测试数据".getBytes()));
        fc.close();
        fc= new FileInputStream("D:\\test2").getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BSIZE);
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());
        buff.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.println("用"+encoding+"编码"+ Charset.forName(encoding).decode(buff));

        fc= new FileOutputStream("D:\\test2").getChannel();
        fc.write(ByteBuffer.wrap("一些记录到文件的".getBytes("UTF-16BE")));
        fc.close();
        fc= new FileInputStream("D:\\test2").getChannel();
        buff.clear();
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());
        fc= new FileOutputStream("D:\\test2").getChannel();
        buff = ByteBuffer.allocate(24);
        buff.asCharBuffer().put("这是测试数据局");
        fc.write(buff);
        fc.close();
        fc= new FileInputStream("D:\\test2").getChannel();
        buff.clear();
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());
    }
}
