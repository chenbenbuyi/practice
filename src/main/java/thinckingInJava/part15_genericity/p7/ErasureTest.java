package thinckingInJava.part15_genericity.p7;

import java.util.*;

/**
 * @author chen
 * @date 2020/10/12 23:06
 * @Description 关于泛型擦除
 *  泛型类型只有在静态类型检查期间才会出现，编译过程中，程序中所有的泛型类型都讲被擦除，替换为非泛型的上界。
 *      List<T>被擦除为 List,其边界上界将被擦除为 Object
 */
public class ErasureTest {
    public static void main(String[] args) {
        /**
         * 以下两种写法实际上都等价于 ArrayList.class 注意，没有类似于 ArrayList<String>.class的形式的写法
         * 编译器编译后的写法：  Class<? extends ArrayList> aClass = (new ArrayList()).getClass();
         */
        Class<? extends ArrayList> aClass = new ArrayList<String>().getClass();
        Class<? extends ArrayList> aClass1 = new ArrayList<Integer>().getClass();
        System.out.println(aClass==aClass1);

        List<Frob> list = new ArrayList<>();
        Map<Frob, Fnorkle> map = new HashMap<>();
        Quark<Fnorkle> quark = new Quark<>();
        /**
         * 对main方法而言，虽然写在类中，它是游离于任何类之外的，因此某类的非静态内部类对它而言是不直接可见的，也就无法直接访问，无法直接new对象，必须要通过外部类的引用才可以访问。
         */
//        Particle<Long,Double> p = new ErasureTest().new Particle<>();
        Particle<Long,Double> p = new Particle<>();

        //getTypeParameters 获取的其实是表示泛型参数的占位标识符
        System.out.println(Arrays.toString(list.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(map.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(quark.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(p.getClass().getTypeParameters()));

    }

    class Frob{}
    class Fnorkle{}
    static class Quark<Q>{}
//    class Particle<POSITION,MEMENTUM>{}
    static class Particle<POSITION,MEMENTUM>{}

    /**
     *  示例方法
     */
    public void createObj(){
        /**
         * not an enclosing class
         */
//        new OuterClass.InnerClass();   not an enclosing class
        new OuterClass().new InnerClass();
        new OuterClass.StaticInnerClass();
    }

}
