package thinckingInJava.part16.p7_arrays;

import lombok.extern.slf4j.Slf4j;
import thinckingInJava.part16.comm.Generated;
import thinckingInJava.part16.comm.RandomGenerator;

import java.util.Arrays;
import java.util.Collections;

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
        while (true){
            Integer next = gen.next();
            /**
             * 对排序数组中的元素进行二分查找。注意，二分查找对于未排序的或有重复数据的数组，返回的结果是不确定的
             * 返回值很有意思：
             *  ① 如果找到元素，则返回元素索引下标
             *  ② 如果没找到元素，返回值为负数，负数的绝对值部分为查找元素的插入点的位置，即数组中第一个比查找的目的值大的元素的索引位置，该位置索引从 1 开始
             */
            int index = Arrays.binarySearch(array, next);
            if(index>0){
                log.info("next 在数组中的索引位置："+index+" 值为："+array[index]);
                break;
            }
        }
    }
}
