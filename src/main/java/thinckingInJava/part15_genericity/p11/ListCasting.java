package thinckingInJava.part15_genericity.p11;

import java.util.*;

/**
 * @author chen
 * @date 2020/11/4 23:04
 * @Description 泛型集合如 List 的转型
 */
public class ListCasting {

    public static void main(String[] args) {
        List<String> strings = Arrays.asList("你好", "我好");
        Map<String, Object> test = new HashMap<String,Object>() {
            {
                put("test", strings);
            }
        };
        Object object = test.get("test");
        List<String> strings2 = List.class.cast(object);
        List<String> strings3= (List<String>)object;
        System.out.println(strings);
        System.out.println(strings2);
        System.out.println(strings3);
        System.out.println(Objects.equals(strings, strings2));
        System.out.println(Objects.equals(strings, strings3));
    }
}
