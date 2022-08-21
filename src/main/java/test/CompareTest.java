package test;

/**
 * @author chen
 * @date 2022/8/21 21:57
 * @Description
 */
public class CompareTest {

    public static void main(String[] args) {

        /**
         *  先根据策略表达式，变量子项表达式列表，根据遍历的子项获取策略比较器，再调用策略比较器进行比较
         *  比较结果用hash存储
         */
        String zimianliang = "COMPONENT_HIGHEST_RISK";
        StrategyCompare strategyCompare = StrategyCompare.get(zimianliang);
//        strategyCompare.strategyCompare(null, , );

    }
}
