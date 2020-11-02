package thinckingInJava.part15_genericity.p4;

import thinckingInJava.part15_genericity.p3.Generator;

/**
 * @author chen
 * @date 2020/10/12 22:56
 * @Description
 */
public class My {
    public static void main(String[] args) {
        new Generator<String>() {
            @Override
            public String next() {
                return null;
            }
        };
    }
}
