package chenbenbuyi.coupon.calculation.service;

import chenbenbuyi.coupon.calculation.api.beans.ShoppingCart;
import chenbenbuyi.coupon.calculation.api.beans.SimulationOrder;
import chenbenbuyi.coupon.calculation.api.beans.SimulationResponse;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author chen
 * @date 2023/2/22 22:58
 * @Description
 */
public interface CouponCalculationService {

    // todo  研究service 接口用 @RequestBody  注解？
    ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart cart);

    SimulationResponse simulateOrder(@RequestBody SimulationOrder cart);
}