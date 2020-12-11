package thinckingInJava.part17_collection.p11_collections;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author chen
 * @date 2020/12/11 0:27
 * @Description Collections 中一些静态工具方法的使用练习
 */
@Slf4j
public class CollectionsTest {

    //    static List<String> list = Arrays.asList("壹 贰 叁 肆 伍 陆 柒 捌 玖 拾 壹".split(" "));
    static List<String> list = Arrays.asList("one Two three Four five six one".split(" "));
    static List<String> sublist = Arrays.asList("Four five six".split(" "));
    static List<String> source = Arrays.asList("int long double".split(" "));

    public static void main(String[] args) {
        log.info("{}", list);
        /**
         * Collections.disjoint 当两个集合没有交集时，返回true
         */
        log.info("{}", "'list' 和 单个肆集合是否有交集？：" + Collections.disjoint(list, Collections.singletonList("肆")));
        log.info("list中的最大值；{}，最小值：{}", Collections.max(list), Collections.min(list));
        /**
         * Collections.max(collection,Comparator) 通过自定义的比较器来获取列表中的最大值
         * String.CASE_INSENSITIVE_ORDER 为 String 类中的Comparator<String> 比较器对象 ，利用ascii 码中的字符顺序进行比较排序
         */
        log.info("list中的最大值2：{}，最小值2：{}", Collections.max(list, String.CASE_INSENSITIVE_ORDER), Collections.min(list, String.CASE_INSENSITIVE_ORDER));
        // Collections.indexOfSubList 返回指定源列表中指定目标列表的第一次出现的起始位置，如果没有此类事件，则返回-1。
        log.info("list中子列表第一次出现的位置：{}", Collections.indexOfSubList(list, sublist));
        // replaceAll 用新值替换列表中所有指定的旧值
        Collections.replaceAll(list, "one", "One");
        log.info("替换后的列表：{}", list);
        // reverse 翻转列表
        Collections.reverse(list);
        log.info("翻转后的列表：{}", list);
        // rotate 旋转 ,distance 值可正可负数或为0 示例中为 +2 则将列表最后的元素排到最前面，其它位置元素顺序不变 。多测试体会
        Collections.rotate(list, 2);
        log.info("rotate后的列表：{}", list);
        // 用给定列表覆盖指定列表中相应索引位置的值  src 列表不能比dest列表长
        Collections.copy(list, source);
        log.info("copy后的列表：{}", list);
        // 交换指定列表中指定的两个索引位置的元素
        Collections.swap(list, 0, list.size() - 1);
        log.info("交换后的列表：{}", list);
        // 使用指定（也可默认）随机源随机排列列表
        Collections.shuffle(list, new Random(47));
        log.info("用给定随机源随机排列后的列表：{}", list);
        // 返回指定列表中于指定对象相等的元素个数
        log.info("相同的元素个数为：{}", Collections.frequency(list, "five"));
        // 返回指定对象的n个副本组成的不可变列表
        List<String> nCopies = Collections.nCopies(3, "chenbenbuyi");
        log.info("不可变列表为：{}", nCopies);
        // 判断两个指定的元素是否没有交集 没有交集返回 true
        log.info("有交集否：{}", Collections.disjoint(list, nCopies));
    }
}
