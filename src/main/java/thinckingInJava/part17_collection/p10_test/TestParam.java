package thinckingInJava.part17_collection.p10_test;

/**
 * @author chen
 * @date 2020/12/1 23:17
 * @Description 封装测试容器的参数
 */
public class TestParam {
    /**
     *  size 标示 容器中的元素个数
     *  loops 循环，控制测试迭代的次数
     */
    public final int size;
    public final int loops;

    public TestParam(int size, int loops) {
        this.size = size;
        this.loops = loops;
    }

    public static TestParam[] array(int... values) {
        int size = values.length / 2;
        TestParam[] result = new TestParam[size];
        int n = 0;
        for (int i = 0; i < size; i++) {
            result[i] = new TestParam(values[n++], values[n++]);
        }
        return result;
    }

    public static TestParam[] array(String[] values) {
        int[] vals = new int[values.length];
        for (int i = 0; i < vals.length; i++) {
            /**
             *  Integer.decode 将字符串解码为Integer。
             *  不同于 Integer.parseInt,decode能够解码 二进制、八进制、十进制、十六进制等
             */
            vals[i] = Integer.decode(values[i]);
        }
        return array(vals);
    }
}
