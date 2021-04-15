package thinckingInJava.part14_class.extra;

/**
 * @date: 2021/4/15 9:12
 * @author: chen
 * @desc: 问：有哪几种获取类 Class 对象的方式，哪些会触发类的初始化，哪些不会触发？
 * 1、类名.class  不会
 * 2、类对象.class  类都实例化了，肯定先就初始化了
 * 3、Class.forName  会
 * 4、类加载器加载类  不会
 * <p>
 * 问：对于当前方法入口所在的内部类的情况，是否结论一致？
 * 1. 两个类仅仅是在编写的时候在同一个类文件中（编译后其实是两个类文件）的情况，结论和两个类无区别.其实，这种情况，都不能称之为严格意义上的内部类了，只不过编码的时候组织在一起罢了，编译后还是两个类
 * 2、（静态）内部类的情况，结论相同，但是要注意这种类的全限定名的写法：
 *      thinckingInJava.part14_class.extra.ClassInitializedTest$MyClass2 ，内部类和外部类需用$符号连接，否则会报找不到类
 */
public class ClassInitializedTest {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<MyClass2> myClassClass = MyClass2.class;
//        System.out.println(myClassClass.hashCode());
//        MyClass2 myClass = new MyClass2();
//        Class<? extends MyClass2> aClass = myClass.getClass();
//        System.out.println(aClass.hashCode());
        Class<?> aClass1 = Class.forName("thinckingInJava.part14_class.extra.ClassInitializedTest$MyClass2");
        System.out.println(aClass1.hashCode());
//        ClassLoader classLoader = ClassInitializedTest.class.getClassLoader();
//        Class<?> aClass2 = classLoader.loadClass("thinckingInJava.part14_class.extra.ClassInitializedTest$MyClass2");
    }


    /**
     *  错误的全限定名写法： thinckingInJava.part14_class.extra.ClassInitializedTest.MyClass2
     *  正确的全限定名写法： thinckingInJava.part14_class.extra.ClassInitializedTest$MyClass2
     */
    protected static class MyClass2 {

        static {
            System.out.println("MyCalss2 初始化");
        }
    }


}

//class MyClass2 {
//
//    static {
//        System.out.println("MyCalss2 初始化");
//    }
//}