package chenbenbuyi.api.commonmistakes.clientdata.trustclientcalculation;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private long itemId;
    private int quantity;
}
