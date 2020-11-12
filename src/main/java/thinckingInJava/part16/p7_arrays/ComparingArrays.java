package thinckingInJava.part16.p7_arrays;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author chen
 * @date 2020/11/12 23:51
 * @Description 数组的比较
 */
@Slf4j
public class ComparingArrays {
    public static void main(String[] args) {
        int[] i = new int[10];
        int[] j = new int[11];
        Arrays.fill(i, 100);
        Arrays.fill(j, 100);
        log.info(Arrays.equals(i, j)+"");
        String[] s1 = new String[5];
        /**
         *  Arrays.equals 针对不同的数组类型，重载了很多的方法，而其底层说到底，还是在针对相应类型的每个元素值进行比较
         */
        Arrays.fill(s1, "陈本布衣");
        String[] s2 = {"陈本布衣","陈本布衣","陈本布衣","陈本布衣","陈本布衣"};
        log.info(Arrays.equals(s1, s2)+"");
    }
}
