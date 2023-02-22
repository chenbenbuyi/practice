package chenbenbuyi.coupon.calculation.template.impl;

import chenbenbuyi.coupon.calculation.api.beans.ShoppingCart;
import chenbenbuyi.coupon.calculation.template.AbstractRuleTemplate;

/**
 * @author chen
 * @date 2023/2/22 22:52
 * @Description 空实现
 */
public class DummyTemplate extends AbstractRuleTemplate {

    @Override
    public ShoppingCart calculate(ShoppingCart order) {
        // 获取订单总价
        Long orderTotalAmount = getTotalPrice(order.getProducts());

        order.setCost(orderTotalAmount);
        return order;
    }


    @Override
    protected Long calculateNewPrice(Long orderTotalAmount, Long shopTotalAmount, Long quota) {
        return orderTotalAmount;
    }
}
