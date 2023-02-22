package chenbenbuyi.coupon.calculation.service.impl;

import chenbenbuyi.coupon.calculation.api.beans.ShoppingCart;
import chenbenbuyi.coupon.calculation.api.beans.SimulationOrder;
import chenbenbuyi.coupon.calculation.api.beans.SimulationResponse;
import chenbenbuyi.coupon.calculation.service.CouponCalculationService;
import chenbenbuyi.coupon.calculation.template.CouponTemplateFactory;
import chenbenbuyi.coupon.calculation.template.RuleTemplate;
import chenbenbuyi.template.api.beans.CouponInfo;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author chen
 * @date 2023/2/22 22:59
 * @Description
 */
@Slf4j
@Service
public class CouponCalculationServiceImpl implements CouponCalculationService {

    @Autowired
    private CouponTemplateFactory couponProcessorFactory;

    // 优惠券结算
    // 这里通过Factory类决定使用哪个底层Rule，底层规则对上层透明
    @Override
    public ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart cart) {
        log.info("calculate order price: {}", JSONUtil.toJsonStr(cart));
        RuleTemplate ruleTemplate = couponProcessorFactory.getTemplate(cart);
        return ruleTemplate.calculate(cart);
    }


    // 对所有优惠券进行试算，看哪个最赚钱
    @Override
    public SimulationResponse simulateOrder(@RequestBody SimulationOrder order) {
        SimulationResponse response = new SimulationResponse();
        Long minOrderPrice = Long.MAX_VALUE;

        // 计算每一个优惠券的订单价格
        for (CouponInfo coupon : order.getCouponInfos()) {
            ShoppingCart cart = new ShoppingCart();
            cart.setProducts(order.getProducts());
            cart.setCouponInfos(Lists.newArrayList(coupon));
            cart = couponProcessorFactory.getTemplate(cart).calculate(cart);

            Long couponId = coupon.getId();
            Long orderPrice = cart.getCost();

            // 设置当前优惠券对应的订单价格
            response.getCouponToOrderPrice().put(couponId, orderPrice);

            // 比较订单价格，设置当前最优优惠券的ID
            if (minOrderPrice > orderPrice) {
                response.setBestCouponId(coupon.getId());
                minOrderPrice = orderPrice;
            }
        }
        return response;
    }

}