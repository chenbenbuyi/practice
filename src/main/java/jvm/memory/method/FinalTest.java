package jvm.memory.method;

/**
 * @author chen
 * @date 2021/5/16 13:08
 * @Description 编译器常量探索
 *  编译期常量指的就是程序在编译时就能确定这个常量的具体值;非编译期常量就是程序在运行时才能确定常量的值，因此也称为运行时常量.由于编译期常量在编译时就确定了值，使用编译期常量的地方在编译时就会替换成对应的值
 *
 * 编译下面类，通过javap -v FinalTest.class > static.txt 命令查看反编译的字节码有何不同
 * 结果是：final修饰的 b/c 和 str都在编译期就确定值，a和str1则无法在编译期间确定初始值
 *
 *  实际上我们一般将编译期常量声明为public static final类型（静态常量），在这种情况下，引用编译期常量才不会导致类的初始化。
 *  本来引用static变量会引起类加载器加载常量所在类，并进行初始化，但由于是编译期常量，编译器在编译引用这个常量的类时，会直接将常量替换为对应值，也就无需再去加载常量所在的类了。
 *  因此，也就必须指出编译期常量的使用风险：
 *      A类定义了一个编译期常量，B类中使用了这个常量，两者都进行了编译。然后修改了A中常量的值，重新进行编译时，系统只会重新编译改动的A类，而旧代码B没有重新编译。导致了B中常量值与A中常量值的不一致。
 *
 */
public class FinalTest {
    static {
        System.out.println("类初始化，如果不是static的，其它类在引用该类的编译器常量时，必须要初始化类");
    }
    static int a = 10;
    final int b = 20;
    static final int c = 30;

    final String str = "字符串常量";
    final String str1 = new String("dasd");
}
