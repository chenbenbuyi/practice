package chenbenbuyi.commonmistakes.httpinvoke.ribbonretry;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableFeignClients(basePackages = "chenbenbuyi.commonmistakes.httpinvoke.ribbonretry.feign")
public class AutoConfig {
}
