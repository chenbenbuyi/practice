package thinckingInJava.part15_genericity.p10;

import java.util.Arrays;
import java.util.List;

/**
 * @author chen
 * @date 2020/10/27 22:52
 * @Description
 */
public class GenericReading {
    static <T> T readExact(List<T> list) {
        return list.get(0);
    }

    static List<Apple> apples = Arrays.asList(new Apple());
    static List<Fruit> fruit = Arrays.asList(new Fruit());

    static void f1() {
        Apple a = readExact(apples);
        Fruit f = readExact(fruit);
        Fruit f2 = readExact(apples);
    }

    /**
     * 由于类上声明了泛型T,所以方法返回值前面不需要<T>占位符
     */
    static class Reader<T> {
        T readExact(List<T> list) {
            return list.get(0);
        }
    }

    static void f2() {
        Reader<Fruit> fruitReader = new Reader<>();
        Fruit f = fruitReader.readExact(fruit);
//        fruitReader.readExact(apples); 无法编译 本质就是 List<Fruit> list = new ArrayList<Apple>();
    }

    /**
     *  该类就是Reader<T>泛型类（确切参数类型）的改进
     */
    static class CovarianReader<T>{
        T readCovariant(List<? extends T> list){
            return list.get(0);
        }
    }

    static void f3(){
        CovarianReader<Fruit> fruitCovarianReader = new CovarianReader<>();
        fruitCovarianReader.readCovariant(fruit);
        fruitCovarianReader.readCovariant(apples);
    }

    public static void main(String[] args) {
        f1();
        f2();
        f3();
    }
}
