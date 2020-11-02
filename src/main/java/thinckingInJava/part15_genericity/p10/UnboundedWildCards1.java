package thinckingInJava.part15_genericity.p10;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chen
 * @date 2020/10/28 0:18
 * @Description 无界通配符 ？，就是不知道
 * 本类系列示例中，无界统配符的作用更像是一种似乎可有可无的装饰。但是基于泛型语法的<?>还是像编译器表明，声明代码并不是想用原生类型，而是任何类型
 *  对于原生类型 List,表示可以持有任何的Object类型的原生List，对于List<?>，由于泛型擦除，编译后其实也等价于List<Object>。所以，List和List<?>更多的是编译话术上的区别：
 *      List表示可持有任何Object类型的原生List,而List<?>则表示具有某种特定类型的非原生List,具体类型是什么，不知道
 */
public class UnboundedWildCards1 {
    static List list1;
    static List<?> list2;
    static List<? extends Object> list3;
    static void assign1(List list){
        list1 = list;
        list2 = list;
        list3 = list;
    }

    static void assign2(List<?> list){
        list1 = list;
        list2 = list;
        list3 = list;
    }

    static void assign3(List<? extends Object> list){
        list1 = list;
        list2 = list;
        list3 = list;
    }

    public static void main(String[] args) {
        assign1(new ArrayList());
        assign2(new ArrayList());
        assign3(new ArrayList());
        assign1(new ArrayList<String>());
        assign2(new ArrayList<String>());
        assign3(new ArrayList<String>());
        List<?> wildList = new ArrayList<>();
        assign1(wildList);
        assign2(wildList);
        assign3(wildList);
    }
}
