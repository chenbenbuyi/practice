package chenbenbuyi.coupon.calculation.template.impl;

import chenbenbuyi.coupon.calculation.template.AbstractRuleTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author chen
 * @date 2023/2/22 22:54
 * @Description 随机立减
 */

@Slf4j
@Component
public class RandomReductionTemplate extends AbstractRuleTemplate {

    @Override
    protected Long calculateNewPrice(Long totalAmount, Long shopAmount, Long quota) {
        // 计算使用优惠券之后的价格
        Long maxBenefit = Math.min(shopAmount, quota);
        int reductionAmount = new Random().nextInt(maxBenefit.intValue());
        Long newCost = totalAmount - reductionAmount;

        log.debug("original price={}, new price={}", totalAmount, newCost);
        return newCost;
    }
}
