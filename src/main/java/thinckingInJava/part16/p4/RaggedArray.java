package thinckingInJava.part16.p4;

import java.util.Arrays;
import java.util.Random;

/**
 * @author chen
 * @date 2020/11/8 10:16
 * @Description
 */
public class RaggedArray {

    public static void main(String[] args) {
        Random random = new Random(43);
        /**
         *  数组的定义你会发现，一维数组的时候，是必须要指定容量的
         *  但是，多为数组的情况下，除了第一维需要定义时指定容量，其它维度并没有强制要求必须定义
         */
        int[] a = new int[3];
        int[][][] b = new int[3][][];
        // 三维数组的静态初始化
        int[][][] c = new int[][][]{{{3}},{{3}}};
        System.out.println(Arrays.deepToString(c));
        /**
         *  三维数组的容量动态定义
         */
        // 第一维容量是确定的
        for (int i = 0; i < b.length; i++) {
            /**
             *  对于一维数组，初始化容量后直接赋值，形如a[i]=i;
             *  对于多维数组，形如上面的三维数组b,此刻的 b[i]= new int[4][5]表示的是赋予第二维数组的容量，
             *  对于多维数组来说，只有最内层的维度才是表示具体数据的，所以b[i]就不是表示某个值了，而是表示的数组定义，而示例中第三维的数字是无效的
             */
            a[i] = i;
            // 第二维
            b[i]=new int[random.nextInt(3)][];
            for (int i1 = 0; i1 < b[i].length; i1++) {
                // 第三维
                b[i][i1] = new int[random.nextInt(5)];
            }
        }
        System.out.println(Arrays.deepToString(b));
    }
}
