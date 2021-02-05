package thinckingInJava.part12_exception;

/**
 * @author chen
 * @date 2020/12/14 22:59
 * @Description 关于NoClassDeFoundError和ClassNotFoundException的区别比较
 * ClassNotFoundException 属于受检查的异常，是需要捕获的，常见的如jdbc驱动的加载，由于其接口和实现相分离，当运行时找不到对应的驱动类是，抛出ClassNotFoundException异常
 * 所以你才会看到加载数据库驱动是捕获异常的写法：
 * try {
 * Class.forName("")
 * } catch (ClassNotFoundException e) {
 * e.printStackTrace();
 * }
 * NoClassDeFoundError ，在java的异常体系同属于Error,意味这是不能捕获的，无法恢复的错误。
 * 通常意味着类在编译时能找到合适的类，但是运行的时候找不到或者能找到但是对应的类不可用，比如关联类含有静态代码块但代码块内容初始化异常
 */
public class NoClassDeFoundErrorTest {
    public static void main(String[] args) {
//        ABC abc = new ABC();
        try {
            //第一次需要加载StaticInitClz类，JVM会加载该类，初始化该类的静态变量或执行静态块
            new StaticInitClz();
        } catch (Throwable t) {
            //因为初始化静态变量失败，所以加载类失败。
            t.printStackTrace();
        }
        new StaticInitClz();
    }
}


/**
 *  估计也只有脑残或者测试的时候才会这样去命名类
 *  在Linux上运行着两个类编译的程序没有问题，因为 Linux是区分文件名大小写的
 *  但是在Windows上，由于文件名并不区分大小写（尝试在同一目录下保存同英文单词大小写不同的文件），所以，编译后，在Windows系统的编译目录下，abc.class将会覆盖掉ABC.class(编译器只管编译，它是不知道操作系统文件限制的)
 *  于是，就出现了编译时ABC和abc类都找到，但是运行时却找不到的问题,就好似你在classpath目录下将本该存在的class编译文件删除掉了一样
 *
 */
class ABC {
    static {
        System.out.println("大ABC");
    }
}

class abc extends ABC{
    static {
        System.out.println("小abc");
    }
}




