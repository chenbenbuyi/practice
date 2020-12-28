package thinckingInJava.part18_io.p10_nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * @author chen
 * @date 2020/12/29 0:24
 * @Description 通过相应视图操作 ByteBuffer
 *  注意：对于视图的任何修改，都会反映到其对应数据主体的修改
 */
public class IntBufferTest {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(new int[]{23,34,5,34,5,34,23,42323});
        System.out.println(intBuffer.get(4));
        intBuffer.put(4,5555);
        intBuffer.flip();
        while (intBuffer.hasRemaining()){
            int i = intBuffer.get();
            System.out.println(i);
        }
        System.out.println(intBuffer.toString());
    }
}
