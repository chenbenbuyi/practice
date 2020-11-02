package thinckingInJava.part15_genericity.p4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author chen
 * @date 2020/10/11 23:34
 * @Description 泛型方法和可变参数
 */
public class GenericArgs {

    /**
     * ① 泛型方法的写法是在返回值前标注泛型参数列表 如 <T> <K,V>等形式
     * ② 可变参数和泛型并不冲突矛盾
     */
    public static <T> List<T> makeList(T... args) {
        if (args == null)
            return Collections.emptyList();
        return Arrays.asList(args);
    }

    public static void main(String[] args) {
        List<Object> objects = GenericArgs.makeList("");
        System.out.println(objects);
    }
}
