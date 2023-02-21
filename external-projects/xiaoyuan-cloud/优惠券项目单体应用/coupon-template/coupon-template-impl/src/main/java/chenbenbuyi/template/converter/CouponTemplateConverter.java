package chenbenbuyi.template.converter;

import chenbenbuyi.template.api.beans.CouponTemplateInfo;
import chenbenbuyi.template.dao.entity.CouponTemplate;

/**
 * @author chen
 * @date 2023/2/21 22:23
 * @Description
 */
public class CouponTemplateConverter {

    public static CouponTemplateInfo convertToTemplateInfo(CouponTemplate template) {

        return CouponTemplateInfo.builder()
                .id(template.getId())
                .name(template.getName())
                .desc(template.getDescription())
                .type(template.getCategory().getCode())
                .shopId(template.getShopId())
                .available(template.getAvailable())
                .rule(template.getRule())
                .build();
    }
}