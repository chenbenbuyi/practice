package thinckingInJava.part16.comm;

import thinckingInJava.part15_genericity.p3.Generator;
import java.util.Random;

/**
 * @author chen
 * @date 2020/9/4 23:35
 * @Description 随机对象生成器
 */
public class RandomGenerator {

    private static Random r = new Random(47);

    public static class Boolean implements Generator<java.lang.Boolean> {
        @Override
        public java.lang.Boolean next() {
            return r.nextBoolean();
        }
    }

    public static class Byte implements Generator<java.lang.Byte> {
        @Override
        public java.lang.Byte next() {
            return (byte) r.nextInt();
        }
    }

    public static class Character implements Generator<java.lang.Character> {
        @Override
        public java.lang.Character next() {
            return CountingGenerator.chars[r.nextInt(CountingGenerator.chars.length)];
        }
    }

    public static class String extends CountingGenerator.String {
        {
            cg = new Character();
        }

        public String() {
        }

        public String(int length) {
            super(length);
        }
    }

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

    public static class Long implements Generator<java.lang.Long> {
        private int mod = 10000;

        public Long() {
        }

        public Long(int mod) {
            this.mod = mod;
        }

        @Override
        public java.lang.Long next() {
            return new java.lang.Long(r.nextInt(mod));
        }
    }

    public static class Float implements Generator<java.lang.Float> {

        @Override
        public java.lang.Float next() {
            int trimmed = Math.round(r.nextFloat() * 100);
            return (float) trimmed / 100;
        }
    }

    public static class Double implements Generator<java.lang.Double> {
        @Override
        public java.lang.Double next() {
            long trimmed = Math.round(r.nextDouble() * 100);
            return (double) trimmed / 100;
        }
    }

}
