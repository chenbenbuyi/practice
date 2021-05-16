package collection.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author chen
 * @date 2021/2/23 7:29
 * @Description 问： HashMap有多少种迭代方式,下面会告诉你答案
 * 参考：https://mp.weixin.qq.com/s/Zz6mofCtmYpABDL1ap04ow
 */
public class MapIterationTest {
    static Map<Integer, String> map;

    static {
        map = new HashMap<>();
        map.put(1, "Java");
        map.put(2, "JDK");
        map.put(3, "Spring Framework");
        map.put(4, "MyBatis framework");
        map.put(5, "Java中文社群");
    }
    public static void main(String[] args) {
        // 1 迭代器——利用 entrySet 或 keySet
        Set<Map.Entry<Integer, String>> entries = map.entrySet();
        Iterator<Map.Entry<Integer, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        // foreach——利用 entrySet 或 keySet
        Set<Map.Entry<Integer, String>> entries2 = map.entrySet();
        for (Map.Entry<Integer, String> entry : entries2) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        // Lambda 表达式遍历
        map.forEach((key, value) -> {
            System.out.println(key + ":" + map.get(key));
        });

        // Stream 遍历
        map.entrySet().stream().forEach((entry)->{
            System.out.println(entry.getKey() + ":" + entry.getValue());
        });

        // Stream 多线程遍历——其底层使用Fork/Join框架实现
        map.entrySet().parallelStream().forEach((entry)->{
            System.out.println(entry.getKey() + ":" + entry.getValue());
        });
    }
}
