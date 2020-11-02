package thinckingInJava.part15_genericity.p10;

import java.util.List;

/**
 * @author chen
 * @date 2020/10/27 22:06
 * @Description 上界通配符无法插入，下界通配符则是无法从中获取
 */
public class SuperTypeTest {

    /**
     *  List<? super Apple> 表示下界通配符，这里是将Apple作为类型的下边界，表示参数化的类型可能是Apple或者Apple类型的父类型，直至 Object
     */
    static void writeTo(List<? super Apple> apples){
        apples.add(new Apple());
        apples.add(new Jonathan());
//        apples.add(new Fruit());
    }
}
