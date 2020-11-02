package thinckingInJava.part19_enum.p7;

import java.util.Random;

/**
 * @author chen
 * @date 2020/8/16 10:05
 * @Description 随机选取
 */
public class Enums {
    private static Random random = new Random(47);

    public static <T extends Enum<T>> T random(Class<T> clz) {
        // getEnumConstants()  返回此枚举类的元素，如果此Class对象不表示枚举类型，则返回null。
        return random(clz.getEnumConstants());
    }

    public static <T> T random(T[] values) {
        return values[random.nextInt(values.length)];
    }
}
