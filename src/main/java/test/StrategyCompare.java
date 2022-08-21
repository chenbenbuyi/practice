package test;

import java.util.Arrays;
import java.util.List;

/**
 * @author chen
 * @date 2022/8/21 21:50
 * @Description
 */
public enum StrategyCompare {
    /**
     * 组件最高风险数
     */
    COMPONENT_HIGHEST_RISK {
        @Override
        void strategyCompare(Component component, List<String> lists) {
            // todo 操作最高风险比较逻辑
            // 实现几种比较符的业务逻辑
            lists.add(component.getLevel() + "违反最高风险");
        }
    },
    /**
     * 组件名称
     */
    COMPONENT_NAME {
        @Override
        void strategyCompare(Component component, List<String> lists) {
            // todo 组件名称比较逻辑
            lists.add(component.getName() + "违反");
        }
    };

    /**
     *  传入的参数应该是组件和策略信息以及记录比较结果值的容器
     * @param component
     * @param lists
     */
    abstract void strategyCompare(Component component, List<String> lists);

    public static StrategyCompare get(String zimianliang) {
        StrategyCompare strategyCompare1 = Arrays.stream(StrategyCompare.values()).filter(strategyCompare ->
                strategyCompare.name().equals(zimianliang)
        ).findAny().get();
        return strategyCompare1;
    }

}
