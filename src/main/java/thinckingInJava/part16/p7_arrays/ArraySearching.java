package thinckingInJava.part16.p7_arrays;

import lombok.extern.slf4j.Slf4j;
import thinckingInJava.part16.comm.Generated;
import thinckingInJava.part16.comm.RandomGenerator;

import java.util.Arrays;

/**
 * @author chen
 * @date 2020/11/14 0:02
 * @Description 对已经排序的数组进行查找
 */
@Slf4j
public class ArraySearching {
    public static void main(String[] args) {
        RandomGenerator.Integer gen = new RandomGenerator.Integer(1000);
        Integer[] array = Generated.array(new Integer[20], gen);
        log.info("未排序数组"+Arrays.toString(array) );
        Arrays.sort(array);
        log.info("排序数组"+Arrays.toString(array) );
    }
}
