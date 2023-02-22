package chenbenbuyi.coupon.calculation.template.impl;

import chenbenbuyi.coupon.calculation.template.AbstractRuleTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @author chen
 * @date 2023/2/22 22:53
 * @Description 午夜10点到次日凌晨2点之间下单，优惠金额翻倍
 */

@Slf4j
@Component
public class LonelyNightTemplate extends AbstractRuleTemplate {


    @Override
    protected Long calculateNewPrice(Long orderTotalAmount, Long shopTotalAmount, Long quota) {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (hourOfDay >= 23 || hourOfDay < 2) {
            quota *= 2;
        }

        Long benefitAmount = shopTotalAmount < quota ? shopTotalAmount : quota;
        return orderTotalAmount - benefitAmount;
    }
}
