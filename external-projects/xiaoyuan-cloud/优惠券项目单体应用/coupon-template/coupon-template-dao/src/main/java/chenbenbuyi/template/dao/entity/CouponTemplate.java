package chenbenbuyi.template.dao.entity;

import chenbenbuyi.template.api.beans.rules.TemplateRule;
import chenbenbuyi.template.api.enums.CouponType;
import chenbenbuyi.template.dao.converter.CouponTypeConverter;
import chenbenbuyi.template.dao.converter.RuleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chen
 * @date 2023/2/21 21:53
 * @Description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
// 声明了“数据库实体”对象，它是数据库 Table 在程序中的映射对象；
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon_template")
public class CouponTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 状态是否可用
    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "name", nullable = false)
    private String name;

    // 适用门店-如果为空，则为全店满减券
    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "description", nullable = false)
    private String description;

    // 优惠券类型
    @Column(name = "type", nullable = false)
    @Convert(converter = CouponTypeConverter.class)
    private CouponType category;

    // 创建时间，通过@CreateDate注解自动填值（需要配合@JpaAuditing注解在启动类上生效）
    @CreatedDate
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    // 优惠券核算规则，平铺成JSON字段
    @Column(name = "rule", nullable = false)
    // Convert：如果数据库中存放的是 code、string、数字等等标记化对象，可以使用 Convert 注解指定一个继承自 AttributeConverter 的类，将 DB 里存的内容转化成一个 Java 对象 可以结合方法名称来加深理解
    @Convert(converter = RuleConverter.class)
    private TemplateRule rule;

}