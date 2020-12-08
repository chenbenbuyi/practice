package thinckingInJava.part17_collection.p3_6;

import lombok.extern.slf4j.Slf4j;
import thinckingInJava.comm.model.User;
import thinckingInJava.part17_collection.p2.Countries;

import java.util.*;

/**
 * @author chen
 * @date 2020/11/24 22:36
 * @Description
 */
@Slf4j
public class CollectionTest {
    public static void main(String[] args) {
        List<String> names = Countries.names();
        Object[] objects = names.toArray();
        // toArray(T[] a)  主要的好处是可以确定返回的数组类型
        String[] strings = names.toArray(new String[0]);
        log.info(Arrays.toString(objects));
        log.info(Arrays.toString(strings));
        // 按照集合中元素的自然排序： 字典序排序，不分大小写
        log.info(Collections.max(names));
        /**
         *  通过自定义的比较规则进行最大(或最小)值的获取 本示例中采用了Java8最新的表达式写法
         *   其中，双冒号 :: 的写法，表示 类名::方法名，这是Lambda的进一步抽象化演进，试体会：
         *   person -> person.getAge()  变为 Person::getAge   注意，方法名没有括号
         *
         */
        log.info(Collections.max(names, (o1, o2) -> o1.hashCode() - o2.hashCode()));
        log.info(Collections.max(names, Comparator.comparingInt(String::hashCode)));
        List<String> cnames = Arrays.asList(new String[]{"中国", "米国", "日本", "朝鲜", "俄罗斯", "巴西", "英国", "澳大利亚"});
        // 保留包含在参数集合中的元素
        names.retainAll(cnames);
        log.info("retainAll：{}", names);
    }
}
