package chenbenbuyi.coupon.customer.service;

import chenbenbuyi.coupon.calculation.api.beans.ShoppingCart;
import chenbenbuyi.coupon.calculation.api.beans.SimulationOrder;
import chenbenbuyi.coupon.calculation.api.beans.SimulationResponse;
import chenbenbuyi.coupon.customer.api.beans.RequestCoupon;
import chenbenbuyi.coupon.customer.api.beans.SearchCoupon;
import chenbenbuyi.coupon.customer.dao.entity.Coupon;
import chenbenbuyi.template.api.beans.CouponInfo;

import java.util.List;

/**
 * @author chen
 * @date 2023/3/26 22:21
 * @Description
 */

// 用户对接服务
public interface CouponCustomerService {
    // 领券接口
    Coupon requestCoupon(RequestCoupon request);

    // 核销优惠券
    ShoppingCart placeOrder(ShoppingCart info);

    // 优惠券金额试算
    SimulationResponse simulateOrderPrice(SimulationOrder order);

    // 用户删除优惠券
    void deleteCoupon(Long userId, Long couponId);

    // 查询用户优惠券
    List<CouponInfo> findCoupon(SearchCoupon request);
    // xxx其它方法请参考源码
}