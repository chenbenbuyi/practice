package thinckingInJava.part16.p6;

import thinckingInJava.part15_genericity.p3.Generator;
import thinckingInJava.part17.com.CollectionData;

import java.lang.reflect.Array;

/**
 * @author chen
 * @date 2020/9/5 21:01
 * @Description
 */
public class Generated {

    // 数组填充
    public static <T> T[] array(T[] a, Generator<T> gen) {
        return new CollectionData<T>(gen, a.length).toArray(a);
    }

    // 数组创建
    public static <T> T[] array(Class<T> type, Generator<T> gen, int size) {
        // 初始化一个执行类型和长度的数组
        T[] a = (T[]) Array.newInstance(type, size);
        // 先创建一个List，然后将List转换成一个数组
        return new CollectionData<>(gen, size).toArray(a);
    }
}
