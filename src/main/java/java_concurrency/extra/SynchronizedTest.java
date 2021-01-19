package java_concurrency.extra;

import java.util.concurrent.TimeUnit;

/**
 * @date: 2021/1/19 9:19
 * @author: chen
 * @desc: 网文引发的一些思考：构造函数是线程安全的吗，怎么保证的现场安全？ synchronized 关键字可以修饰构造器吗？
 * 这里根据有博文说发做如下表述：
 * 构造器是用于对象创建的，不同并发调用构造器产生的是不同的对象，并不涉及共享资源问题，因此并不需要进行同步化，所以构造函数式线程安全的；
 * 但是在构造函数一开始，this就是可用的，所以在对象构造的时候要注意引用逸出的问题，因为逸出可能导致另外的线程访问到还未安全初始化的对象实例
 */
public class SynchronizedTest {
    private String str;
    public static SynchronizedTest obj;

    /**
     * 实测发现：对于构造方法，甚至都无法添加 synchronized，无法编译
     */
//    public synchronized SynchronizedTest() {
    public SynchronizedTest() {
        // 引用逸出
        obj = this;
        try {
            TimeUnit.SECONDS.sleep(5);
            str = "默认构造器";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void doSomething() {
        System.out.println("成员变量str的长度是：" + str.length());
    }

    /**
     *  引用逸出
     */
    public static void main(String[] args) {
        SynchronizedTest.obj.doSomething();
    }
}
