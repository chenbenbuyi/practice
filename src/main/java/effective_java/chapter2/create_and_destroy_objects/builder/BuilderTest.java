package effective_java.chapter2.create_and_destroy_objects.builder;

import static effective_java.chapter2.create_and_destroy_objects.builder.NyPizza.Size.SMALL;
import static effective_java.chapter2.create_and_destroy_objects.builder.Pizza.Topping.*;

/**
 * @author chen
 * @date 2023/1/18 11:09
 * @Description
 */
public class BuilderTest {

    public static void main(String[] args) {
        ResourceConfig config = ResourceConfig.builder()
                .setName("资源配置对象")
                .setMaxTotal(16)
                .setMaxIdle(10)
                .setMinIdle(12)
                .build();

        NyPizza pizza = new NyPizza.Builder(SMALL).add(SAUSAGE).add(ONION).build();

        Calzone calzone = new Calzone.Builder().add(HAM).sauceInside().build();
    }
}
