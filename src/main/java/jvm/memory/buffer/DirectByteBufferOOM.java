package jvm.memory.buffer;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author chen
 * @date 2021/5/20 22:00
 * @Description 直接内存的溢出测试
 * 控制参数
 * -XX:MaxDirectMemorySize=10m
 */
public class DirectByteBufferOOM {
    private static final int _5M = 5 * 1024 * 1024;

    public static void main(String[] args) throws InterruptedException, IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        int i = 0;
        while (true) {
            System.out.println("第" + i++ + "次分配");
            unsafe.allocateMemory(_5M);
        }
    }
}
