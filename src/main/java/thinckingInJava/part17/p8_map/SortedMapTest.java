package thinckingInJava.part17.p8_map;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * @author chen
 * @date 2020/11/28 10:10
 * @Description
 */
@Slf4j
public class SortedMapTest {
    public static void main(String[] args) {
        /**
         *  和 SortedSet 有点类似，TreeMap 也是排序Map SortedMap 的唯一实现
         *  TreeMap 基于红黑树实现的排序Map,次序有Comparable 或Comparator 决定
         *  TreeMap 是唯一有subMap 方法的Map (排序嘛，自然好截取)
         */
        TreeMap<Integer, String> sortedMap = new TreeMap<>(new CountingMapData(10));
        log.info("{}", sortedMap);
        Integer firstKey = sortedMap.firstKey();
        Integer lastKey = sortedMap.lastKey();
        log.info("第一个key:{},最后一个Key:{}", firstKey, lastKey);
        Iterator<Integer> it = sortedMap.keySet().iterator();
        for (int i = 0; i < 7; i++) {
            if (i == 3) firstKey = it.next();
            if (i == 6) lastKey = it.next();
            else it.next();
        }
        log.info("map截取，左闭右开：{}",sortedMap.subMap(firstKey, lastKey));
        log.info("map截取，小于指定键的map集合：{}",sortedMap.headMap(firstKey));
        log.info("map截取，大于等于指定键的map集合：{}",sortedMap.tailMap(lastKey));
    }
}
