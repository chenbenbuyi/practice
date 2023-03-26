package chenbenbuyi.coupon.customer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author chen
 * @date 2023/3/26 22:01
 * @Description 优惠券状态枚举
 */

@Getter
@AllArgsConstructor
public enum CouponStatus {
    AVAILABLE("未使用", 1),
    USED("已用", 2),
    INACTIVE("已经注销", 3);

    private String desc;
    private Integer code;

    public static CouponStatus convert(Integer code) {
        if (code == null) {
            return null;
        }
        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElse(null);
    }
}
