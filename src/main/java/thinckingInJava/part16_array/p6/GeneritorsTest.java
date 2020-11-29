package thinckingInJava.part16_array.p6;

import lombok.extern.slf4j.Slf4j;
import thinckingInJava.part16_array.comm.CountingGenerator;
import thinckingInJava.part15_genericity.p3.Generator;
import thinckingInJava.part16_array.comm.Generated;

import java.util.Arrays;

/**
 * @author chen
 * @date 2020/11/8 15:43
 * @Description
 */
@Slf4j
public class GeneritorsTest {

    public static int size = 10;

    public static void test(Class<?> clz) {
        // getClasses得到该类及其父类所有的public的内部类。
        for (Class<?> type : clz.getClasses()) {
            log.info("{}", type.getSimpleName());
            try {
                Generator<?> generator = (Generator<?>) type.newInstance();
                for (int i = 0; i < size; i++) {
                    log.info("{}", generator.next());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void test2() {
        Integer[] arr = {9, 4, 6, 3};
        arr = Generated.array(arr, new CountingGenerator.Integer());
        log.info(Arrays.toString(arr));
        Integer[] array = Generated.array(Integer.class, new CountingGenerator.Integer(), 4);
        log.info(Arrays.toString(array));
    }

    public static void main(String[] args) {
//        GeneritorsTest.test(CountingGenerator.class);
        GeneritorsTest.test2();
    }
}
