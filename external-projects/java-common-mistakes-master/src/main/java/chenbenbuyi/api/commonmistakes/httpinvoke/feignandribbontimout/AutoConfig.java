package chenbenbuyi.api.commonmistakes.httpinvoke.feignandribbontimout;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "chenbenbuyi.commonmistakes.httpinvoke.feignandribbontimout")
public class AutoConfig {
}
