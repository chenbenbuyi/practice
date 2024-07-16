package geek.designer.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @date: 2020/12/14 19:16
 * @author: chen
 * @desc: 利用java8 的函数式代码api实现另类的策略模式（策略接口+不同的策略接口实现+分派逻辑）
 * <p>
 * Function<T, R> ，其函数式接口为 apply，输入参数T ,返回参数 R
 * 如果 T和R 是相同类型参数，可以使用其继承接口 UnaryOperator 。
 * @see <a href="https://www.cnblogs.com/hollischuang/p/13186766.html">参考代码</a>
 */


@Slf4j
public class FunctionTest {

    private enum Phone {
        XIAOMI("小米工厂"),
        IPHONE("爱疯工厂"),
        HUAWEI("华为工厂");

        private String name;

        Phone(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 定义分配逻辑映射关系 在分配逻辑固定的时候，可用枚举来避免参数传入问题
     */
    private Map<String, Function<String, String>> dispatcherMap = new HashMap<>();
    //    private Map<String, UnaryOperator<String>> dispatcherMap = new HashMap<>();
    private EnumMap<Phone, Function<Phone, String>> dispatcherEnumMap = new EnumMap<>(Phone.class);

    public void testBefore() {
        dispatcherMap.put("校验1", order -> String.format("对%s执行业务逻辑1", order));
        dispatcherMap.put("校验2", order -> String.format("对%s执行业务逻辑2", order));
        dispatcherMap.put("校验3", order -> String.format("对%s执行业务逻辑3", order));
        dispatcherEnumMap.put(Phone.XIAOMI, Phone::getName);
        dispatcherEnumMap.put(Phone.HUAWEI, Phone::getName);
        dispatcherEnumMap.put(Phone.IPHONE, Phone::getName);
    }


    /**
     * 利用策略map,根据输入参数获取结果。这个结果可以是某个值、或其它更为复杂的业务逻辑代码等，具体根据实际情况封装
     */
    private String checkResultStr(String strategy) {
        // 获取的是函数式接口
        Function<String, String> function = dispatcherMap.get(strategy);
        if (strategy != null && function != null) {
            return function.apply(strategy);
        }
        return "默认情况";
    }

    /**
     * 利用策略map,根据输入参数获取结果。这个结果可以是某个值、或其它更为复杂的业务逻辑代码等，具体根据实际情况封装
     */
    private String checkResultEnum(Phone phone) {
        // 获取的是函数式接口
        Function<Phone, String> function = dispatcherEnumMap.get(phone);
        return function.apply(phone);
    }


    @Test
    public void strategyTest() {
        testBefore();
        log.info("获取的策略结果：{}", checkResultEnum(Phone.XIAOMI));
        log.info("获取的策略结果：{}", checkResultStr("校验3"));
        log.info("获取的策略结果：{}", checkResultStr("校验7"));
    }
}
