package chenbenbuyi.api.commonmistakes.concurrenttool.threadlocal;

import chenbenbuyi.api.commonmistakes.common.Utils;
import chenbenbuyi.commonmistakes.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        Utils.loadPropertySource(CommonMistakesApplication.class, "tomcat.properties");

        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

