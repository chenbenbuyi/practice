package chenbenbuyi.coupon.customer.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author chen
 * @date 2023/3/26 22:06
 * @Description 封装优惠券查询的请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCoupon {
    @NotNull
    private Long userId;
    private Long shopId;
    private Integer couponStatus;
}