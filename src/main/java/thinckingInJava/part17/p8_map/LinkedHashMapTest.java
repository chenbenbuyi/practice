package thinckingInJava.part17.p8_map;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;

/**
 * @author chen
 * @date 2020/11/28 11:03
 * @Description
 */
@Slf4j
public class LinkedHashMapTest {
    public static void main(String[] args) {
        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>(new CountingMapData(10));
        log.info("初始化的linkedMap：{}", linkedHashMap);
        linkedHashMap.get(0);

        /**
         *  通过初始容量、负载因子和订购模式构造对象
         *  其中，accessOrder,表示迭代顺序，默认为false,按照插入顺序迭代元素。设置为true,则会使用了LRU——最近最少被使用的调度算法对元素进行迭代
         */
        linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);
        linkedHashMap.putAll(new CountingMapData(10));
        log.info("初始化的linkedMap2：{}", linkedHashMap);
        for (int i = 0; i < 6; i++) {
            String s = linkedHashMap.get(i);
            log.info("遍历数据：{}",s);
        }
        /**
         *  由于采用LRU 调度算法，此打印中，for循环中别访问过的元素将被排在后面
         *  注意，迭代顺序是不是元素的取值顺序。由于其是Map,取值根据的是键，和顺序无关
         */
        log.info("{}",linkedHashMap);
        linkedHashMap.get(0);
        log.info("{}",linkedHashMap);
    }
}
