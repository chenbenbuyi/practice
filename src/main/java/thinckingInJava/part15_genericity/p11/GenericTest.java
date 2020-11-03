package thinckingInJava.part15_genericity.p11;

import thinckingInJava.part15_genericity.p3.Generator;

/**
 * @date: 2020/11/3 14:06
 * @author: chen
 * @desc:
 */

public class GenericTest {
    public static void main(String[] args) {
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
