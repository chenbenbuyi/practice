package thinckingInJava.part15_genericity.p2;

/**
 * @author chen
 * @date 2020/10/9 21:46
 * @Description
 */

class Amphibian {
}

class Vehicle {
}

public class TupleTest {
    static TwoTuple<String, Integer> f() {
        return new TwoTuple<>("陈本布衣", 25);
    }

    static ThreeTuple<Amphibian, String, Integer> g() {
        return new ThreeTuple<>(new Amphibian(), "陈本布衣", 30);
    }

    static FourTuple<Vehicle, Amphibian, String, Integer> h() {
        return new FourTuple<>(new Vehicle(), new Amphibian(), "陈本布衣", 30);
    }


    public static void main(String[] args) {
        /**
         *  你可以看到，通过创建使用元组对象，可以实现参数化的形式通过单个方法返回多个未定对象
         */
        TwoTuple<String, Integer> f = f();
        String first = f.first;
        System.out.println(f());
        System.out.println(g());
        System.out.println(h());
    }
}
