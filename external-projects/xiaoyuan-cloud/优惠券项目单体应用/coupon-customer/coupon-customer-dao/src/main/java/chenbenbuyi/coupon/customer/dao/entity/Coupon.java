package chenbenbuyi.coupon.customer.dao.entity;

import chenbenbuyi.coupon.customer.dao.convertor.CouponStatusConverter;
import chenbenbuyi.coupon.customer.enums.CouponStatus;
import chenbenbuyi.template.api.beans.CouponTemplateInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author chen
 * @date 2023/3/26 22:09
 * @Description
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 对应的模板ID - 不使用one to one映射
    @Column(name = "template_id", nullable = false)
    private Long templateId;

    // 拥有这张优惠券的用户的ID
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 冗余一个shop id方便查找
    @Column(name = "shop_id")
    private Long shopId;

    // 自动生成时间戳
    @CreatedDate
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    // CouponStatusConverter实现了AttributeConverter接口
    // 将数据库value转化为CouponStatus类
    @Column(name = "status", nullable = false)
    @Convert(converter = CouponStatusConverter.class)
    private CouponStatus status;

    @Transient
    private CouponTemplateInfo templateInfo;
}