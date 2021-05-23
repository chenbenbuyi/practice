package collection.set;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chen
 * @date 2021/5/22 12:42
 * @Description set 只存储不重复的元素，其判重依据在于哈希值和equals是否同时相等。
 * 对字符串的添加，有一些令人疑惑的地方。请分析如下对字符串的添加示例：
 *
 *
 */
public class HashSetTest {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        System.out.println("chen".equals(new String("chen")));
        System.out.println("chen".hashCode()==new String("chen").hashCode());
        System.out.println(new String("chen").equals(new String("chen")));
        System.out.println(new String("chen").hashCode()==new String("chen").hashCode());
        set.add("chen");
        set.add(new String("chen"));
        set.add(new String("chen"));
        /**
         *  结论就是，不管new了多少个String对象，只要其字面量值一样，都算是重复元素，只能添加一个。
         *  虽然new了两个String对象，但是HashSet添加元素判重是基于哈希值和equals判断
         *  上面的情况中，相同字面量不同String对象的哈希值是完全相同，因为String重写了哈希方法
         */
        System.out.println(set);

    }
}
