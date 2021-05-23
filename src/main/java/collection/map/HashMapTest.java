package collection.map;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chen
 * @date 2021/5/22 15:43
 * @Description 测试HashMap添加元素的返回值
 * 结论：如果添加key未映射过元素，则返回null，如果key已经存在，则返回key之前关联的旧值
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        String res = map.put("chen", "帅哥");
        System.out.println(res);
        String res2 = map.put("chen", "丑鬼");
        System.out.println(res2);
    }
}
