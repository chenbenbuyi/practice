package chenbenbuyi.coupon.calculation.controller;

import chenbenbuyi.coupon.calculation.api.beans.ShoppingCart;
import chenbenbuyi.coupon.calculation.api.beans.SimulationOrder;
import chenbenbuyi.coupon.calculation.api.beans.SimulationResponse;
import chenbenbuyi.coupon.calculation.service.CouponCalculationService;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chen
 * @date 2023/2/22 22:57
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("calculator")
public class CouponCalculationController {

    @Autowired
    private CouponCalculationService calculationService;

    // 优惠券结算
    @PostMapping("/checkout")
    @ResponseBody
    public ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart settlement) {
        log.info("do calculation: {}", JSONUtil.toJsonStr(settlement));
        return calculationService.calculateOrderPrice(settlement);
    }

    // 优惠券列表挨个试算
    // 给客户提示每个可用券的优惠额度，帮助挑选
    @PostMapping("/simulate")
    @ResponseBody
    public SimulationResponse simulate(@RequestBody SimulationOrder simulator) {
        log.info("do simulation: {}", JSONUtil.toJsonStr(simulator));
        return calculationService.simulateOrder(simulator);
    }
}