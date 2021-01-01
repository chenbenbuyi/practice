package thinckingInJava.part18_io.p10_nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @author chen
 * @date 2020/12/29 22:32
 *  将一个字节数组写到文件中：通常是用 ByteBuffer.wrap将自己数组包装成 ByteBuffer(缓冲区)，然后在输出流 FileOutputStream 上打开一个输出通道（FileOutputStream.getChannel）,然后将ByteBuffer中缓冲的数据通过通道写入文件
 *  再次强调：ByteBuffer 是将数据移入和移除通道的唯一方式。其它基本数据类型对应的缓冲器通过 ByteBuffer ‘as’相关方法获得其相应视图，通过视图缓冲器将基本类型数据移入和移出缓冲器
 *  缓冲器由数据和可以高效的访问和操纵数据的四个索引组成：mark(标记),position(位置),limit(界限),capacity(容量)，其相关常用的方法如下：
 *  capacity() 返回缓冲器容量
 *  clear() 清空缓冲区，设置 position 为0，设置 limit 为容量。调用此方法可以覆写缓冲区
 *  flip() 设置limit 为 position, 设置 position 为 0,丢弃标记 mark。 调用此方法用来准备从缓冲区读取已经写入的数据
 *  limit() 返回值
 *  limit(int lim) 设置值
 *  mark()  设置mark为position
 *  position 返回值
 *  position(int pos) 设置值
 *  remaining()  返回 limit-position，即当前位置和限制之间的元素数
 *  hasRemaining() 若有介于position和limit之间的元素，返回true
 *  reset() 将缓冲器的位置(position)设置为之前标记(mark)的位置
 */
public class UsingBuffers {
    private static void symmetricScramble(CharBuffer charBuffer) {
        while (charBuffer.hasRemaining()) {
            charBuffer.mark();
            char c1 = charBuffer.get();
            char c2 = charBuffer.get();
            /**
             *  get和put都会导致position位置的增加
             *  reset 将此缓冲区的位置重置为先前标记的位置。 调用此方法既不会更改也不丢弃该标记的值。
             */
            charBuffer.reset();
            charBuffer.put(c2).put(c1);
        }
    }

    public static void main(String[] args) {
        /**
         *  ？？？ 这里，不同的字符串输入，有的正常，有的却会抛出 BufferUnderflowException 异常,why?
         *
         */
        char[] chars = "UsingBuffers".toCharArray();
        ByteBuffer buffer = ByteBuffer.allocate(chars.length * 2);
        CharBuffer charBuffer = buffer.asCharBuffer();
        charBuffer.put(chars);
        // 直接打印，只会打印position到limit之间的字符，如果要显示全部内容，需要通过rewind方法，将position设置到缓冲器的最开始位置
        System.out.println(charBuffer.rewind());
        symmetricScramble(charBuffer);
        System.out.println(charBuffer.rewind());
        symmetricScramble(charBuffer);
        System.out.println(charBuffer.rewind());
    }
}
