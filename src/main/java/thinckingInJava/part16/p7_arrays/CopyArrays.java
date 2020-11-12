package thinckingInJava.part16.p7_arrays;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author chen
 * @date 2020/11/12 23:43
 * @Description
 */
@Slf4j
public class CopyArrays {
    public static void main(String[] args) {
        int[] i = new int[6];
        int[] j = new int[10];
        // 用给定值填充指定数组的每个元素
        Arrays.fill(i, 99);
        Arrays.fill(j, 100);
        log.info(Arrays.toString(i));
        /**
         *  仅仅演示arraycopy Api. 这是java标准类库中们对于数组拷贝的支持，比单独for循环进行替换效率要高得多，因为它是本地方法
         *  注意，对于引用数据类型，拷贝的只是对象的引用，并不是对象本身，是所谓 浅拷贝
         */
        System.arraycopy(i, 0, j, 0, i.length);
        log.info(Arrays.toString(j));
    }
}
