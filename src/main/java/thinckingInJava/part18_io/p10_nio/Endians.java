package thinckingInJava.part18_io.p10_nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author chen
 * @date 2020/12/29 0:34
 * @Description
 *  不同系统有不同的字节排序方式，网络数据传送或大多数系统都采用高位优先的大端模式
 *  大端模式（big endian）高位优先，将最重要的字节存在地址最低的存储器单元（也即高字节保存在低地址位），小端模式（little endian）则正好相反
 *  当存储大于一个字节的数据如int,float等，就要考虑大小端模式的对数据的影响
 *  ByteBuffer 默认是以大端模式存储数据
 */
public class Endians {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[12]);
        buffer.asCharBuffer().put("abcdef");
        // array 返回缓冲区的字节数组序列
        System.out.println(Arrays.toString(buffer.array()));
        buffer.rewind();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(buffer.array()));
        buffer.rewind();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(buffer.array()));

    }
}
