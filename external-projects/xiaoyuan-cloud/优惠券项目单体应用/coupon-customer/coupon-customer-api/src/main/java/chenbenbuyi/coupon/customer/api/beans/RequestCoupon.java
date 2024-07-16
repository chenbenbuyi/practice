package chenbenbuyi.coupon.customer.api.beans;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chen
 * @date 2023/3/26 22:00
 * @Description 用户领取优惠券请求参数对象
 * 通过传入用户 ID 和优惠券模板 ID，用户可以领取一张由指定模板打造的优惠券
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCoupon {

    // 用户领券
    @NotNull
    private Long userId;

    // 券模板ID
    @NotNull
    private Long couponTemplateId;

}