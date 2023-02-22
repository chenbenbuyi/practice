package chenbenbuyi.coupon.calculation.template;

import chenbenbuyi.coupon.calculation.api.beans.ShoppingCart;

/**
 * @author chen
 * @date 2023/2/22 22:43
 * @Description
 */
public interface RuleTemplate {

    // 计算优惠券
    ShoppingCart calculate(ShoppingCart settlement);
}
