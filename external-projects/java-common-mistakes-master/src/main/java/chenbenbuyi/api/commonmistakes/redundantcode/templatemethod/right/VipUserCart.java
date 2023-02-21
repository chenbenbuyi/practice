package chenbenbuyi.api.commonmistakes.redundantcode.templatemethod.right;

import chenbenbuyi.api.commonmistakes.redundantcode.templatemethod.Db;
import chenbenbuyi.api.commonmistakes.redundantcode.templatemethod.Item;
import chenbenbuyi.commonmistakes.redundantcode.templatemethod.Db;
import chenbenbuyi.commonmistakes.redundantcode.templatemethod.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service(value = "VipUserCart")
public class VipUserCart extends NormalUserCart {

    @Override
    protected void processCouponPrice(long userId, Item item) {
        if (item.getQuantity() > 2) {
            item.setCouponPrice(item.getPrice()
                    .multiply(BigDecimal.valueOf(100 - Db.getUserCouponPercent(userId)).divide(new BigDecimal("100")))
                    .multiply(BigDecimal.valueOf(item.getQuantity() - 2)));
        } else {
            item.setCouponPrice(BigDecimal.ZERO);
        }
    }
}
