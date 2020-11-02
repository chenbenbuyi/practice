package thinckingInJava.part19_enum.p4;

import lombok.extern.slf4j.Slf4j;
import thinckingInJava.part19_enum.p1.Two;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author chen
 * @date 2020/9/15 22:46
 * @Description
 * javap Explore 命令反编译出来的结果：

Compiled from "Reflection.java"
final class thinckInJava.part19.p4.Explore extends java.lang.Enum<thinckInJava.part19.p4.Explore> {
public static final thinckInJava.part19.p4.Explore HERE;
public static final thinckInJava.part19.p4.Explore THERE;
public static thinckInJava.part19.p4.Explore[] values();
public static thinckInJava.part19.p4.Explore valueOf(java.lang.String);
static {};

}
 */

enum Explore {
    HERE, THERE;
}

/**
 *  ① 创建的枚举编译器都会隐式的继承自 Enum类（这是一个抽象类），枚举都是被编译器转化成了final的类
 *  ② 编译器还会为你的枚举添加公共基类Enum中并不存在的静态方法如values方法和同基类不一样方法签名的valueOf方法
 *  ③ 编译器还顶一个了一个静态代码块
 *
 */

@Slf4j
public class Reflection {

    public static Set<String> analyze(Class<?> enumClass) {
        log.debug("-------------" + enumClass + " 分析--------------");
        log.debug("Interfaces:");
        for (Type t : enumClass.getInterfaces()) {   // 表示通过该
            log.debug("{}", t);
        }
        log.debug("超类：" + enumClass.getSuperclass() + "\nMethods:");// 返回超类
        TreeSet<String> methods = new TreeSet<>();
        for (Method method : enumClass.getMethods()) {
            methods.add(method.getName());
        }
        System.out.println(methods);
        return methods;
    }

    public static void main(String[] args) {
        Set<String> exporeMethods = analyze(Explore.class);
        Set<String> enumMethods = analyze(Enum.class);
        System.out.println("Explore.containsAll(Enum)?"+exporeMethods.containsAll(enumMethods));
        System.out.println("Explore.removeAll(Enum):"+exporeMethods.removeAll(enumMethods));
        // set 存储的方法名字，所以Enum中的valueOf虽然和编译器生成的valueOf虽然不是同样的方法，但也被移除
        System.out.println(exporeMethods);
        // 通过枚举类的实例的 getEnumConstants 方法也可以获取到枚举值的数组形式
        System.out.println(Arrays.toString(Two.class.getEnumConstants()));

    }
}
