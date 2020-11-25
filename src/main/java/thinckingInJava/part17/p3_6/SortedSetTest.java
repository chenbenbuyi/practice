package thinckingInJava.part17.p3_6;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author chen
 * @date 2020/11/25 23:55
 * @Description
 */
@Slf4j
public class SortedSetTest {
    public static void main(String[] args) {
        SortedSet<String> sortedSet = new TreeSet<>();
        Collections.addAll(sortedSet,"陈,本,布,衣".split(","));
        log.info("{}",sortedSet);
        String first = sortedSet.first();
        String last = sortedSet.last();
        // 返回子集，左闭右开
        sortedSet.subSet(first, last);
        // 返回由小于last的元素组成的子集
        sortedSet.headSet(last);
        // 返回大于或等于first 元素组成的子集
        sortedSet.tailSet(first);
    }
}
