package collection.set;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * @author chen
 * @date 2021/5/23 12:00
 * @Description TreeSet 支持根据自定义比较规则对添加的元素排序
 *  其底层实现是 TreeMap
 *
 */
public class TreeSetTest {

    public static void main(String[] args) {
//        TreeSet<String> treeSet = new TreeSet<>();
//        TreeSet<String> treeSet = new TreeSet<>(String::compareTo);
        // 根据TreeMap 添加对象的源码，如果比较值为0 ，则不会添加新元素
//        TreeSet<String> treeSet = new TreeSet<>((o1, o2) -> 0);
        // 根据长度排序添加，也就意味着，相同长度的元素无法重复添加
        TreeSet<String> treeSet = new TreeSet<>(Comparator.comparingInt(String::length));
        treeSet.add("chen");
        treeSet.add("hao");
        treeSet.add("你看");
        treeSet.add("这个");
        treeSet.add("世界");
        System.out.println(treeSet);

    }
}
