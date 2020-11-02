package thinckingInJava.part15_genericity.p7;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author chen
 * @date 2020/10/14 21:54
 * @Description
 */
public class ArrayMaker<T> {
    private Class<T> type;

    public ArrayMaker(Class<T> type) {
        this.type = type;
    }

    /**
     *  在泛型中创建数组的推荐方式：Array.newInstance(type,size)
     */
    T[] create(int size){
        return (T[])Array.newInstance(type, size);
    }

    public static void main(String[] args) {
        ArrayMaker<String> maker = new ArrayMaker<>(String.class);
        String[] strings = maker.create(10);
        Object o = Array.newInstance(String.class, 23);
        System.out.println(Arrays.toString(strings));

    }
}
