package chenbenbuyi.coupon.customer.dao.convertor;

import chenbenbuyi.coupon.customer.enums.CouponStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author chen
 * @date 2023/3/26 22:13
 * @Description
 */
@Converter
public class CouponStatusConverter implements AttributeConverter<CouponStatus, Integer> {

    // 如果需要把DB里的值转换成enum对象，就采用这种方式就好了
    // 利用泛型模板继承AttributeConverter

    // enum转DB value
    @Override
    public Integer convertToDatabaseColumn(CouponStatus status) {
        return status.getCode();
    }

    // DB value转enum值
    @Override
    public CouponStatus convertToEntityAttribute(Integer code) {
        return CouponStatus.convert(code);
    }
}