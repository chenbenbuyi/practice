package thinckingInJava.part15_genericity.p10;

import thinckingInJava.part15_genericity.p9.BasicBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @date 2020/10/20 23:25
 * <? extends T>和<? super T>是Java泛型中的“通配符（Wildcards）”和“边界（Bounds）”的概念。
 * <? extends T>：是指 “上界通配符（Upper Bounds Wildcards）”
 * <? super T>：是指 “下界通配符（Lower Bounds Wildcards）”
 */
class Fruit {
}

class Apple extends Fruit {
}

class Jonathan extends Apple {
}

class Orange extends Fruit {
}

public class CovariantArrays {
    public static void main(String[] args) {
        Fruit[] fruit = new Apple[10];
        fruit[0] = new Apple();
        fruit[1] = new Jonathan();
        /**
         * 虽然 编译器依据继承关系允许你往数组中添加有继承关系的元素，但数组终究是一个只能放 Apple类型实例的数组
         * 而泛型的主要目标之一就是将这种类似的错误检测机制提前到编译期，通过将参数泛型化，提前阻止不合法的参数类型
         * 这里，数组是有类型协变——考虑方法覆盖的案例，子类覆盖方法的返回值必须与父类方法返回值相同或其子类
         */

//        fruit[3] = new Fruit();
//        fruit[4] = new Orange();
        /**
         * 泛型参数类型虽然有继承关系，但是现在的问题是容器的类型，而不是容器持有的类型
         * 一个Apple的List容器并不等价于一个Fruit的List容器,也就是说容器本身并没有继承关系
         * 这里,泛型并不支持所谓的协变类型
         *
         */
//        List<Fruit> list = new ArrayList<Apple>(); 编译错误

        /**
         *   如果要建立一种类型之间的转型关系，就要使用通配符的形式 形如如{@link BasicBounds}
         */
        List<Apple> list = new ArrayList<>();
        list.add(new Apple());
        list.add(new Apple());
        /**
         * 下面的示例可以发现，一旦执行这种类型之间的向上转型关系，就无法再向容器中添加元素，哪怕是 Object 对象都不行,why?
         *  虽然是 ？号，看似可以理解为任何继承自Fruit的类型，但是并不意味着List中可以持有任何类型的Fruit，通配符 ？ 表示的是符合条件（这里是继承自Fruit）的某一种类型，可以是Apple，也可以是Orange，当然也可以是Fruit本身，具体是什么，不知道。
         *  因为不知道具体类型，所以，编译器不允许我们向 List<? extends Fruit>集合中添加任何元素。
         *      我们假设，可以添加任意Fruit的子类元素，那么这会导致容器里面存放了各种类型的元素，可能有Orange,Apple或者Fruit本身，这就和jdk1.5泛型出现之前的集合一样了，什么都能添加，泛型对于元素的编译期检查就没有任何意义了
         *  但是，由于我们知道该集合中的任何类型数据都是Fruit的子类，所以，从该集合中取值是可以的,当然，取出来的值都是Fruit类型引用，具体转型过程中，只能人为的保证转型成功
         */
        List<? extends Fruit> ls = new ArrayList<Apple>();
        System.out.println(ls);
//        ls.add(new Apple());
//        ls.add(new Object());
    }
}