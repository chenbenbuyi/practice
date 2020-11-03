package thinckingInJava.part15_genericity.p11;

import thinckingInJava.part15_genericity.p3.Generator;
import thinckingInJava.part16.p6.RandomGenerator;

/**
 * @date: 2020/11/3 14:06
 * @author: chen
 * @desc:
 */

public class GenericTest {
    public static void main(String[] args) {
        String[] strings = FArray.fill(new String[7], new RandomGenerator.String(10));
        for (String string : strings) {
            System.out.println(string);
        }
        Integer[] integers = FArray.fill(new Integer[7], new RandomGenerator.Integer());
        for (int integer : integers) {
            System.out.println(integer);
        }
        // 自动装箱、拆箱机制无法适用于数组对象
//        FArray.fill(new int[7], new RandomGenerator.Integer());
    }
}

class FArray {

    public static <T> T[] fill(T[] arr, Generator<T> gen) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = gen.next();
        }
        return arr;
    }
}
