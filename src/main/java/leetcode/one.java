package leetcode;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @date: 2020/11/10 9:20
 * @author: chen
 * @desc: 拉拉杂杂收录归集一些算法题目
 */
@Slf4j
public class one {

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     * <p>
     * 示例:
     * 给定 nums = [2, 7, 11, 15], target = 9
     * 因为 nums[0] + nums[1] = 2 + 7 = 9 所以返回 [0, 1]
     */
    @Test
    public void test1() {
        StopWatch stopWatch = new StopWatch();
        // 普通思维题解：内外层循环，遍历比较判断
        int[] arrs = {2, 5, 10, 7, 4, 11, 15};
        int target = 15;
        stopWatch.start();
        for (int i = 0; i < arrs.length - 1; i++) {
            for (int j = 1 + i; j < arrs.length; j++) {
                if (arrs[i] + arrs[j] == target) {
                    log.info(String.format("两个值的数组索引为：%s,%s，程序执行时间：%s 纳秒", i, j, stopWatch.getTime(TimeUnit.NANOSECONDS)));
                    break;
                }
            }
        }

        stopWatch.reset();
        stopWatch.start();
        // 骚气的操作，利用Hash散列，来节省程序寻址元素的时间。比较可以发现，数组越大，hash散列体现的性能优势越明显
        HashMap<Integer, Integer> hashMap = new HashMap<>(arrs.length);
        for (int i = 0; i < arrs.length; i++) {
            if (hashMap.containsKey(arrs[i])) {
                log.info(String.format("骚操作：两个值的数组索引为：%s,%s，程序执行时间：%s 纳秒", hashMap.get(arrs[i]), i, stopWatch.getTime(TimeUnit.NANOSECONDS)));
            } else {
                hashMap.put((target - arrs[i]), i);
            }
        }
    }

    @Test
    public void test2() {}


    @Test
    public void test3() {}


    @Test
    public void test4() {}


    @Test
    public void test5() {}


    @Test
    public void test6() {}

    @Test
    public void test7() {}


    @Test
    public void test8() {}


    @Test
    public void test9() {}

    @Test
    public void test10() {}

}
