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
@EnableJpaAuditing
//@ComponentScan(basePackages = {"chenbenbuyi"})
@ComponentScan
public class TemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
    }
}
