package thinckingInJava.part18_io.p10_nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @author chen
 * @date 2020/12/27 23:16
 * @Description
 *  缓冲器中容纳的是普通字节，如果要转换成字符，要么在输入时进行编码，要么输出是进行解码
 *  CharSet 就提供了把数据编码成多种不同类型字符集的工具方法
 */
public class BufferToText {
    private static final int BSIZE=1024;

    public static void main(String[] args) throws IOException {
        FileChannel fc  = new FileOutputStream("D:\\test").getChannel();
        /**
         * wrap 将已经存在的字节序列包装到 ByteBuffer 中
         * write(ByteBuffer src)  从给定的缓冲区中向该通道写入一个字节序列
         * read(ByteBuffer dst) 从该通道读取字节序列到给定大小的缓冲区
         */
        fc.write(ByteBuffer.wrap("写的一些测试数据".getBytes()));
        fc.close();
        fc= new FileInputStream("D:\\test").getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BSIZE);
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());

        /**
         * rewind 将position返回到数据开始的部分
         * 使用平台默认字符集对数据进行(Charset)decode,其结果 CharBuffer 就能很好的在控制台输出
         * 举一反三，虽然常用的 ByteBuffer是唯一与通道进行交互的缓冲器，但缓冲器却不只是 ByteBuffer，还有该示例中的
         * CharBuffer ，以及其它的诸如 IntBuffer,LongBuffer,DoubleBuffer等等
         *
         */
        buff.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.println("用"+encoding+"编码后读取的数据："+ Charset.forName(encoding).decode(buff));

        /**
         *  读取时进行编码，在进行字符读取时，不用再进行解码也能输出客答应的字符串
         */
        fc= new FileOutputStream("D:\\test2").getChannel();
        fc.write(ByteBuffer.wrap("同样是一些测试数据".getBytes("UTF-16BE")));
        fc.close();
        fc= new FileInputStream("D:\\test2").getChannel();
        buff.clear();
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());

        /**
         *  测试中，为  ByteBuffer 分配了24个字节，而一个字符是两个字节长度，也就意味着该ByteBuffer 缓冲器可以容纳12个字节
         *  而 测试数据 some text 只有9个字节 ，则缓冲器为0的字节也会补位输出到文件中
         */
        fc= new FileOutputStream("D:\\test2").getChannel();
        buff = ByteBuffer.allocate(24);
        CharBuffer charBuffer = buff.asCharBuffer();
        charBuffer.put("some text");
        fc.write(buff);
        fc.close();
        fc= new FileInputStream("D:\\test2").getChannel();
        buff.clear();
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());
    }
}
