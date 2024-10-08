package chenbenbuyi.template.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author chen
 * @date 2023/2/21 22:04
 * @Description
 */

@Getter
@AllArgsConstructor
public enum CouponType {
    UNKNOWN("unknown", "0"),
    MONEY_OFF("满减券", "1"),

    DISCOUNT("打折", "2"),
    RANDOM_DISCOUNT("随机减", "3"),

    LONELY_NIGHT_MONEY_OFF("晚间双倍优惠券", "4"),

    ANTI_PUA("PUA加倍奉还券", "5");

    private String description;
    // 存在数据库里的最终code
    private String code;

    // UNKNOWN 防御性编程思维
    public static CouponType convert(String code) {
        return Stream.of(values()).filter(bean -> bean.code.equalsIgnoreCase(code)).findFirst().orElse(UNKNOWN);
    }
}