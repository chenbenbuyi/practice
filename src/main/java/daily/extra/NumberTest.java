package daily.extra;

import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Field;

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
        System.out.println(Math.abs(Integer.MIN_VALUE));
        System.out.println(Byte.MIN_VALUE==(byte)-Byte.MIN_VALUE);

        /**
         *  问：是否存在一个数，满足i==i+1 ？
         *  无穷大加一个常数还是无穷大
         */
        Double i = Double.POSITIVE_INFINITY;
        System.out.println(i == i+1);
        System.out.println(i == i-1);

        /**
         *  是否有一个数可以满足i!=i
         */
        double d = Double.NaN;
        System.out.println(d != d);

        int e = 0;
        e=e+++e;
        System.out.println(e);

        // NullPointerException 数组创建时并没有在堆上分配内存，在赋值的时候，也根本找不到地址
//        int a[] = null;
//        a[0]=1;

    }
}
