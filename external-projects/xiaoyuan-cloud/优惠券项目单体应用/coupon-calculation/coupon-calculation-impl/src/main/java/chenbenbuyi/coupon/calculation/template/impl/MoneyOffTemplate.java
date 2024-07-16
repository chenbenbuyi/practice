package chenbenbuyi.coupon.calculation.template.impl;

import chenbenbuyi.coupon.calculation.template.AbstractRuleTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chen
 * @date 2023/2/22 22:47
 * @Description 满减实现类
 */
@Slf4j
@Component
public class MoneyOffTemplate extends AbstractRuleTemplate {


    @Override
    protected Long calculateNewPrice(Long totalAmount, Long shopAmount, Long quota) {
        // benefitAmount是扣减的价格
        // 如果当前门店的商品总价<quota，那么最多只能扣减shopAmount的钱数
        Long benefitAmount = shopAmount < quota ? shopAmount : quota;
        return totalAmount - benefitAmount;
    }
}
