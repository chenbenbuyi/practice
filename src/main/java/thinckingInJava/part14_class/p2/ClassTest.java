package thinckingInJava.part14_class.p2;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author chen
 * @date 2020/9/27 20:30
 * @Description Class 类对象的泛型测试
 */
@Slf4j
public class ClassTest {

    /**
     * jdk 1.5 开始，Class支持泛型化，虽然你可以不用泛型，但是使用泛型明显的好处就是具化类型，限定过于宽泛的输入，在编译期提前检查错误
     */
    @Test
    public void test() {
        Class clz = int.class;
        clz = double.class;
        Class<Integer> integerClass = int.class;
//        integerClass = double.class; 无法编译
        /**
         *  ？ 表示任意类型  在声明的时候，建议都指定具体类型或采用 ？ 的形式，而不是不指定类型
          */
        Class<?> longClass = long.class;
        longClass = double.class;
        /**
         * 限定某种类型及其子类
         */
        Class<? extends Number> intClz = int.class;
        intClz= double.class;
//        intClz = String.class; 无法编译
    }

    @Test
    public void test2() {
        /**
         * 基本类型、数组、接口都可以生成对应类型的Class对象引用
         */
        Class<Integer> integerClass = int.class; // 基本类型可以直接产生其Class引用
        log.info("是否是基本类型：" + integerClass.isPrimitive() + ";" + integerClass.toString());
        integerClass = Integer.TYPE;  // 和上面的方式等价 但基于编程习惯，建议使用类字面常量的形式——即类.class形式
        log.info("是否是基本类型：" + integerClass.isPrimitive() + ";" + integerClass.toString());

        Class<?> myInterfaceClass = MyInterface.class;
        log.info("接口的类型引用对象："+ myInterfaceClass.toString());
        String[] arr = {"陈", "骚", "年"};
        Class<? extends String[]> aClass = arr.getClass();
        log.info("数组的类型应用对象：" + aClass.toString());
    }
}

interface MyInterface {}
