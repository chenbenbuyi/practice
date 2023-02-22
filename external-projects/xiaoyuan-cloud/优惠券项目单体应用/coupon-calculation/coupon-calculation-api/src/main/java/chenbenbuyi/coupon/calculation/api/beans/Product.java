package chenbenbuyi.coupon.calculation.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    // 商品的价格
    private long price;
    /**
     * 商品在购物车里的数量
     * 商品的数量通常不能以 Integer 整数来表示，这是因为像像蔬菜、肉类等非标品来说，它们的计件单位并不是“个”。
     * 所以在实际项目中，尤其是零售行业的业务系统里，计件单位要允许小数位的存在。此处简化业务简单处理
     */
    private Integer count;
    // 商品销售的门店
    private Long shopId;
}