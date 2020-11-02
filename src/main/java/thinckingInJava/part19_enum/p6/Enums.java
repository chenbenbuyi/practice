package thinckingInJava.part19_enum.p6;

import java.util.Random;

/**
 * @author chen
 * @date 2020/9/16 21:19
 * @Description 随机返回枚举实例
 */
public class Enums {
    private static Random rand = new Random(47);

    // 注意泛型的方法体上的声明形式 这里表示 T 只能是一个Enum类型的实例
    public static <T extends Enum<T>> T random(Class<T> clz) {
        return randomEnum(clz.getEnumConstants());
    }

    public static <T> T randomEnum(T[] vals) {
        return vals[rand.nextInt(vals.length)];
    }
}
