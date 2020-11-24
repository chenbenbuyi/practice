package daily.extra;

import org.openjdk.jol.info.ClassLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * @date: 2020/11/24 18:45
 * @author: chen
 * @desc: 对象布局查看 参考博文：https://www.cnblogs.com/javazhiyin/p/14023183.html
 * 一个java 对象在虚拟机 堆上的存储中，一个对象实际上是包含 对象头（object header）、实例数据(静态属性不属于实例数据)、对齐填充（Java中的对象大小，只能是8的整数倍） 几个部分
 * 以下为最简单的情况，只包含几种基本数据类型，该对象输出：
 * OFFSET  SIZE      TYPE DESCRIPTION                               VALUE
 * 0     4           (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
 * 4     4           (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 * 8     4           (object header)                           05 c1 00 20 (00000101 11000001 00000000 00100000) (536920325)
 * 12     4       int JolTest.a                                 10
 * 16     8      long JolTest.b                                 20
 * 24     1   boolean JolTest.c                                 true
 * 25     7           (loss due to the next object alignment)  对齐填充
 */
public class JolTest {
    private int a = 10;
    private long b = 20;
    private boolean c = true;

    public static void main(String[] args) {
        JolTest jolTest = new JolTest();
        System.out.println(ClassLayout.parseInstance(jolTest).toPrintable());
        System.out.println("===================分割====================");
        JolRefrence jolRefrence = new JolRefrence();
        System.out.println(ClassLayout.parseInstance(jolRefrence).toPrintable());
        System.out.println("===================分割====================");
        JolArra jolArra = new JolArra();
        System.out.println(ClassLayout.parseInstance(jolArra).toPrintable());
    }
}

/**
 * 包含引用数据类型的情况
 * 不同于基本数据类型，对象的实例数据区若为引用数据类型，实际上只是保存了引用对象的地址
 */
class JolRefrence {
    private int a = 10;
    private Object b = new Object();
    Map<String,Object> objMap = new HashMap<>(16);
}

/**
 *  都知道，数组对象有一个表示数组长度的属性字段 length ，其为 int类型，因此你可以这样理解，理论情况下，java中数组允许的最大长度为4个字节 int 类型能表示的最大值大小(在内存足够的前提下)
 *      但实际如果用比较大的数值去测试，会抛出
 *         java.lang.OutOfMemoryError: Java heap space ()  （更常见的错误，要知道，Integer.MAX_VALUE 个长度大小的int数组，差不多要 2G 的内存大小才够）
 *      或 java.lang.OutOfMemoryError: Requested array size exceeds VM limit  （基于该测试环境，Integer.MAX_VALUE -2就是虚拟机平台的数组大小极限值，因为数组对象不同于普通java对象，它会有一个额外的元数据来
 *          表示数组的大小，可以参考ArrayList 源码中数组的最大容量表示： Integer.MAX_VALUE - 8）
 *
 */
class JolArra {
    private int a = 10;
    private int b = 10;
    //对象中无属性的数组
    Object[] objArray = new Object[3];
    //对象中存在两个int型属性的数组
    JolArra[] blogArray = new JolArra[3];

    // 基本类型数组
    int[] intArray = new int[1];

}

