package chenbenbuyi.coupon.calculation.template.impl;

import chenbenbuyi.coupon.calculation.template.AbstractRuleTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chen
 * @date 2023/2/22 22:51
 * @Description 打折优惠券
 */

@Slf4j
@Component
public class DiscountTemplate extends AbstractRuleTemplate  {

    @Override
    protected Long calculateNewPrice(Long totalAmount, Long shopAmount, Long quota) {
        // 计算使用优惠券之后的价格
        Long newPrice = convertToDecimal(shopAmount * (quota.doubleValue()/100));
        log.debug("original price={}, new price={}", totalAmount, newPrice);
        return newPrice;
    }
}