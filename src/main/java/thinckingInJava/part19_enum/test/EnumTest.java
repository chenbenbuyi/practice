package thinckingInJava.part19_enum.test;

import org.junit.jupiter.api.Test;
import thinckingInJava.part19_enum.p1.One;
import thinckingInJava.part19_enum.p1.One2;
import thinckingInJava.part19_enum.p1.Two;

/**
 * @author chen
 * @date 2020/9/15 20:39
 * @Description
 */
public class EnumTest {

    /**
     * 可以看出，对于枚举类型的相等判断，其实就可以简单的进行==判断
     */
    @Test
    public void test1() {
        One[] values = One.values();
        for (One o : values) {
            // compareTo 的比较顺序就是声明的枚举顺序
            System.out.println(o.compareTo(One.CRAWLING));
            System.out.println(o.equals(One.CRAWLING));
            System.out.println(o.equals(One2.CRAWLING));
            System.out.println(o == One.CRAWLING);
            // 返回与此枚举常量的枚举类型相对应的Class对象
            System.out.println(o.getDeclaringClass());
            // name 方法返回枚举常量的名称，和枚举声明值完全一样 和 toString 方法是一个意思
            System.out.println(o.name());
        }
    }

    /**
     * 和 test1中的枚举类型比较
     */
    @Test
    public void test2() {
        Two[] values = Two.values();
        for (Two t : values) {
            System.out.println(t.compareTo(Two.CHEN));
            System.out.println(t.equals(Two.CHEN));
            System.out.println(t == Two.CHEN);
            // 返回与此枚举常量的枚举类型相对应的Class对象
            System.out.println(t.getDeclaringClass());
            // name 方法返回枚举常量的名称，和枚举声明值完全一样 和 toString 方法是一个意思
            System.out.println(t.name());
        }
    }

    @Test
    public void test3() {
        One2[] values = One2.values();
        for (One2 o : values) {
            System.out.println(o.toString());
        }

    }
}
