package collection.set;

import thinckingInJava.part17_collection.p8_map.LinkedHashMapTest;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author chen
 * @date 2021/5/22 19:27
 * @Description LinkedHashSet 相较于其父类 HashSet 不同的地方在于通过双向链表来维护元素之间的顺序。也就是说，LinkedHashSet 的特点在于 有序、不重复
 *  LinkedHashSet 源码简单，其底层功能实现依赖于LinkedHashMap。既然其实现是基于LinkedHashMap，那么LinkedHashSet支持按元素访问顺序排序吗？
 *   结论是，不支持。跟踪其构造函数，底层调用的LinkedHashMap的构造函数中accessOrder值写死的false
 *  参见{@link LinkedHashMapTest} 关于按照LRU调度算法按照元素访问顺序排序的研究
 */
public class LinkedHashSetTest {
    public static void main(String[] args) {
        Set<String> set = new LinkedHashSet<>(16);
        set.add("chen");
        set.add("shao");
        set.add("xian");
        System.out.println(set);
    }
}
