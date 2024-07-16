package chenbenbuyi.coupon.customer.service.impl;

import chenbenbuyi.coupon.customer.dao.entity.Coupon;
import chenbenbuyi.template.api.beans.CouponInfo;

/**
 * @author chen
 * @date 2023/3/26 22:24
 * @Description
 */
public class CouponConverter {

    public static CouponInfo convertToCoupon(Coupon coupon) {

        return CouponInfo.builder()
                .id(coupon.getId())
                .status(coupon.getStatus().getCode())
                .templateId(coupon.getTemplateId())
                .shopId(coupon.getShopId())
                .userId(coupon.getUserId())
                .template(coupon.getTemplateInfo())
                .build();
    }
}