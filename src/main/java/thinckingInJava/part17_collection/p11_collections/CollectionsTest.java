package thinckingInJava.part17_collection.p11_collections;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author chen
 * @date 2020/12/11 0:27
 * @Description Collections 中一些静态工具方法的使用练习
 */
@Slf4j
public class CollectionsTest {

    static List<String> list = Arrays.asList("壹 贰 叁 肆 伍 陆 柒 捌 玖 拾 壹".split(" "));

    public static void main(String[] args) {
        log.info("{}", list);
        /**
         * Collections.disjoint 当两个集合没有交集时，返回true
         */
        log.info("{}", "'list' 和 单个肆集合是否有交集？：" + Collections.disjoint(list, Collections.singletonList("肆")));
        log.info("list中的最大值；{}，最小值：{}",Collections.max(list),Collections.min(list));
        log.info("");
    }
}
