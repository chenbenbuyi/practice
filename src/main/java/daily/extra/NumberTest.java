package daily.extra;

import java.math.BigDecimal;

/**
 * @author chen
 * @date 2021/5/23 21:15
 * @Description 数字魔法，让你意想不到的结局
 */
public class NumberTest {
    public static void main(String[] args) {
        /**
         * 问：负数的绝对值一定是正数吗？
         * Math.abs方法，源码中对负数的处理只是简单的 判断正负，如果是负值，就取负，负负为正
         * 但是，对于特殊的 Integer.MIN_VALUE，取负得正后由于比最大正值大1，导致溢出，变成了负数
         * 相似的情况 Long、Short、Byte 一样
         * 这种情形变化一下，可以问成
         *  a、是否存在一个数i，可以使得i+1<i
         *  b、是否存在一个数，满足i != 0 && i == -i
         *
         */
        System.out.println("Integer最小值的Math.abs求绝对值：" + Math.abs(Integer.MIN_VALUE));
        System.out.println(Byte.MIN_VALUE == (byte) -Byte.MIN_VALUE);

        /**
         *  问：是否存在一个数，满足i==i+1 ？
         *  无穷大加一个常数还是无穷大
         */
        Double i = Double.POSITIVE_INFINITY;
        System.out.println("无穷大数加1和原数据是否相等：" + (i == i + 1));
        System.out.println("无穷大数减1和原数据是否相等：" + (i == i - 1));

        /**
         *  是否有一个数可以满足i!=i
         */
        double d = Double.NaN;
        System.out.println("是否有一个数满足i!=i?NaN 可以满足：" + (d != d));

        /**
         *
         */
        int e = 0;
        e = e++ + e;
        System.out.println("e++ + e的值为：" + e);

        /**
         * 下面的代码会报NullPointerException ，因为数组只是申明了引用，并没有在堆上分配内存，在赋值的时候，也根本找不到地址
         */
        try {
            int a[] = null;
            a[0] = 1;
        } catch (Exception ex) {
        }

        /**
         *  浮点数之间判断
         *  浮点数之间的等值判断，基本数据类型不能用==来比较，包装数据类型不能用 equals 来判断
         *  因为浮点数采用“尾数+阶码”的编码方式，类似于科学计数法的“有效数字+指数”的表示方式。二进制无法精确表示大部分的十进制小数
         */
        System.out.println("直接声明的具体浮点数判断：" + (0.1f == 0.1f));
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        System.out.println(a == b);
        Float x = Float.valueOf(a);
        Float y = Float.valueOf(b);
        System.out.println(x.equals(y));
        /** 正确的判断逻辑是：指定一个误差范围，两个浮点数的差值在此范围之内，则认为是相等的。*/
        float diff = 1e-6f;
        System.out.println(Math.abs(a - b) < diff);
        /** 或者使用 BigDecimal 来定义值，再进行浮点数的运算操作。*/
        BigDecimal ba = new BigDecimal("1.0");
        BigDecimal bb = new BigDecimal("0.9");
        BigDecimal bc = new BigDecimal("0.8");
        BigDecimal bx = ba.subtract(bb);
        BigDecimal by = bb.subtract(bc);
        System.out.println("利用BigDecimal判断浮点数："+bx.equals(by));

    }
}
