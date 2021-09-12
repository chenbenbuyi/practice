package collection.map;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * @author chen
 * @date 2021/5/23 12:27
 * @Description
 */
public class TreeMapTest {
    public static void main(String[] args) {
//        TreeMap<String, String> treeMap = new TreeMap<>();
//        TreeMap<String, String> treeMap = new TreeMap<>((o1, o2) -> 0);
        TreeMap<String, String> treeMap = new TreeMap<>(Comparator.comparingInt(String::length));
        treeMap.put("chen","shao");
        treeMap.put("a","胜多负少类库的房间");
        treeMap.put("bb","sdd");
        treeMap.put("cc","一样的");
        System.out.println(treeMap);
        System.out.println("chen".hashCode()^("chen".hashCode()>>>16));
        System.out.println(3052512&31);
    }
}
