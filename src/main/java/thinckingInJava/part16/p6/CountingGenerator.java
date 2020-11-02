package thinckingInJava.part16.p6;

import thinckingInJava.part15_genericity.p3.Generator;

/**
 * @author chen
 * @date 2020/9/6 11:02
 * @Description
 */
public class CountingGenerator {

    public static class Integer implements Generator<java.lang.Integer> {
        private int value = 0;

        public java.lang.Integer next() {
            return value++;
        }
    }
}
