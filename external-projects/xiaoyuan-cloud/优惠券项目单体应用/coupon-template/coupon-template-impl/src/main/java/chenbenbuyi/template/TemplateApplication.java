package chenbenbuyi.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author chen
 * @date 2023/2/21 22:29
 * @Description
 */

@SpringBootApplication
/**
 *  单机版本中，如果在用户中心模块启动服务，则该注解不需要，否则容器中会有两个@EnableJpaAuditing注解的Bean：
 *  The bean 'jpaAuditingHandler' could not be registered. A bean with that name has already been defined and overriding is disabled.
 *
 *  @EnableJpaAuditing 注解的作用是开启 @CreatedDate 等注解的自动填充
 */
//@EnableJpaAuditing
//@ComponentScan(basePackages = {"chenbenbuyi"})
@ComponentScan
public class TemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
    }
}
