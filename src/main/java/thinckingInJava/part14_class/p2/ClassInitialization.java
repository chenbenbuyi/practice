package thinckingInJava.part14_class.p2;

import java.util.Random;

/**
 * @author chen
 * @date 2020/9/26 0:00
 * @Description
 */


public class ClassInitialization {

    public static Random rand = new Random(47);
    public static void main(String[] args) throws ClassNotFoundException {
        Class<InitTest1> initTest1Class = InitTest1.class;
        System.out.println(InitTest1.Final);
        System.out.println(InitTest1.Final2);
        System.out.println(InitTest2.NoFinal);
        Class.forName("thinckingInJava.part14_class.p2.InitTest3");
        System.out.println(InitTest3.NoFinal);
    }
}

class InitTest1 {
    /**
     *  对比测试你会发现：
     *  ① 类名.class的方式（类字面量方式）和Class.forName的方式虽然都可以获取到Class实例引用，但其不会引发类的初始化
     *  ② 如果一个 static final 是编译器常量，也不会导致类初始化，因为可以直接读取
     *  ③ 如果一个 static final 不是编译期常量，会导致类的初始化操作
     *  ④ 如果一个静态域不是final的，则在访问其值时，会先为其进行链接（分配存储空间）和初始化
     */
    static final int Final = 99;
    static final int Final2 = ClassInitialization.rand.nextInt(1000);

    static {
        System.out.println("InitTest1 内的静态代码块");
    }
}

class InitTest2 {
    static int NoFinal = 199;

    static {
        System.out.println("InitTest2 内的静态代码块");
    }
}

class InitTest3 {
    static int NoFinal = 299;

    static {
        System.out.println("InitTest3 内的静态代码块");
    }
}


