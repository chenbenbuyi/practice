package chenbenbuyi.coupon.customer.dao;

import chenbenbuyi.coupon.customer.dao.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chen
 * @date 2023/3/26 22:18
 * @Description
 */

public interface CouponDao extends JpaRepository<Coupon, Long> {
    // 根据用户ID和Template ID，统计用户从当前优惠券模板中领了多少张券
    long countByUserIdAndTemplateId(Long userId, Long templateId);
}