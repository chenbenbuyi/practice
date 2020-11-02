package thinckingInJava.part15_genericity.p10;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @date 2020/10/27 22:45
 * @Description
 */
public class GenericWriting {
    static <T> void writeExact(List<T> list, T item) {
        list.add(item);
    }

    static List<Apple> apples = new ArrayList<>();
    static List<Fruit> fruit = new ArrayList<>();

    static void f1() {
        writeExact(apples, new Apple());
        /**
         *  编程思想中描述该段代码无法编译，应该是一处谬误
         *  虽然无法将子类型泛型对象赋值给父类的类型引用，如 List<Fruit> fruit =  new ArrayList<Apple>(); 肯定无法编译
         *  但是并不妨碍父类型泛型声明的对象添加子类型对象，这是多态的一种表现。
         */
        writeExact(fruit, new Apple());
    }

    static <T> void writeWithWildcard(List<? super T> list,T item){
        list.add(item);
    }

    static void f2() {
        writeWithWildcard(apples, new Apple());
        writeWithWildcard(fruit, new Apple());
    }

    public static void main(String[] args) {
        f1();
        f2();
    }

}
