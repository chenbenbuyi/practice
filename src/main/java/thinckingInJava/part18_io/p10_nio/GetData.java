package thinckingInJava.part18_io.p10_nio;

import java.nio.ByteBuffer;

/**
 * @author chen
 * @date 2020/12/29 0:02
 * @Description ByteBuffer 顾名思义，只能保存字节类型的数据，但它具有从其容纳的字节中产生各种不同基本类型值的方法
 */
public class GetData {
    private static final int BSIZE = 1024;

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(BSIZE);
        int i = 0;
        while (i++ < byteBuffer.limit()) {
            if (byteBuffer.get() != 0) {
                System.out.println("为零值");
            }
        }
        System.out.println("i=" + i);
        byteBuffer.rewind();

        byteBuffer.asCharBuffer().put("hello,chenbenbuyi!");
        char c;
        while ((c = byteBuffer.getChar()) != 0) {
            System.out.print(c + " ");
        }
        System.out.println("");
        byteBuffer.rewind();
        byteBuffer.asShortBuffer().put((short)233);
        System.out.println("getShort获取值："+byteBuffer.getShort());
        byteBuffer.rewind();

        byteBuffer.asIntBuffer().put(12333434);
        System.out.println(byteBuffer.getInt());
        byteBuffer.rewind();

        byteBuffer.asFloatBuffer().put(12333434);
        System.out.println(byteBuffer.getFloat());
        byteBuffer.rewind();
    }
}
