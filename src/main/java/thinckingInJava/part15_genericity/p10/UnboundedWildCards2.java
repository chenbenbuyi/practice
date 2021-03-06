package thinckingInJava.part15_genericity.p10;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chen
 * @date 2020/10/28 22:11
 * @Description
 */
public class UnboundedWildCards2 {
    static Map map1;
    // 这种情况下编译器无法将其和原生类型map1进行区分
    static Map<?, ?> map2;
    static Map<String, ?> map3;

    static void assign1(Map map) {
        map1 = map;
    }

    static void assign2(Map<?,?> map) {
        //        map.put("", "");
        map2 = map;
    }

    static void assign3(Map<String,?> map) {
        // 无法向无界统配符的声明对象中放入具体对象
//        map.put("", new Object());
        map3 = map;
    }

    public static void main(String[] args) {
        assign1(new HashMap(3));
        assign2(new HashMap(2));
        assign3(new HashMap(3));
        assign1(new HashMap<String,Integer>(4));
        assign2(new HashMap<String,Integer>(5));
        assign3(new HashMap<String,Integer>(5));
    }
}
