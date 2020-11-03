package thinckingInJava.part16.p6;

import thinckingInJava.part15_genericity.p3.Generator;

import java.util.Random;

/**
 * @author chen
 * @date 2020/9/4 23:35
 * @Description
 */
public class RandomGenerator {

    private static Random r = new Random(47);

    public static class Integer implements Generator<java.lang.Integer> {
        private int mod = 10000;

        public Integer() {
        }

        public Integer(int mod) {
            this.mod = mod;
        }

        @Override
        public java.lang.Integer next() {
            return r.nextInt(mod);
        }
    }

    public static void main(String[] args) {
    }
}
