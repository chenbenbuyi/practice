package thinckingInJava.part12_exception;

/**
 * @author chen
 * @date 2020/12/14 22:59
 * @Description 关于NoClassDeFoundError和ClassNotFoundException的区别比较
 *  ClassNotFoundException 属于受检查的异常，是需要捕获的，常见的如jdbc驱动的加载，由于其接口和实现相分离，当运行时找不到对应的驱动类是，抛出ClassNotFoundException异常
 *      所以你才会看到加载数据库驱动是捕获异常的写法：
 *           try {
 *                  Class.forName("")
 *            } catch (ClassNotFoundException e) {
 *                 e.printStackTrace();
 *            }
 *   NoClassDeFoundError ，在java的异常体系同属于Error,意味这是不能捕获的，无法恢复的错误。
 *      通常意味着类在编译时能找到合适的类，但是运行的时候找不到或者能找到但是对应的类不可用，比如关联类静态初始化失败
 */
public class NoClassDeFoundErrorTest {
    public static void main(String[] args) {
        C c = new C();
    }

}



class A{
    static {
        System.out.println("大A");
    }
}

class a {
    static {
        System.out.println("小a");
    }
}


class B{
    static {
        System.out.println("大B");
    }
}

class b  extends B{
    static {
        System.out.println("小b");
    }
}

/**
 *  C 和 D 的情形模拟的是关联类静态初始化失败:
 *      由于类D 静态初始化时发生意外（ExceptionInInitializerError），继而导致依赖他的类C也无法正常初始化
 *      ?????????????????????????????
 */
class C{
    static {
         new D();
    }
}
class D{
    static {
       try {
           int i = 1 /0;
       }catch (Exception e){
           throw e;
       }
    }
}



