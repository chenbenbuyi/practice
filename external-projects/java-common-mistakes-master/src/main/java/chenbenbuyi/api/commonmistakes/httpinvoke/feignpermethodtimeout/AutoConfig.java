package chenbenbuyi.api.commonmistakes.httpinvoke.feignpermethodtimeout;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "chenbenbuyi.commonmistakes.httpinvoke.feignpermethodtimeout")
public class AutoConfig {
}
