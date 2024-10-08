package chenbenbuyi.api.commonmistakes.springpart2.aopfeign;

import chenbenbuyi.api.commonmistakes.springpart2.aopfeign.feign.Client;
import chenbenbuyi.api.commonmistakes.springpart2.aopfeign.feign.ClientWithUrl;
import chenbenbuyi.commonmistakes.springpart2.aopfeign.feign.Client;
import chenbenbuyi.commonmistakes.springpart2.aopfeign.feign.ClientWithUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("feignaop")
@RestController
public class FeignAopConntroller {

    @Autowired
    private Client client;

    @Autowired
    private ClientWithUrl clientWithUrl;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("client")
    public String client() {
        return client.api();
    }

    @GetMapping("clientWithUrl")
    public String clientWithUrl() {
        return clientWithUrl.api();
    }

    @GetMapping("server")
    public String server() {
        return "OK";
    }
}
