package thinckingInJava.part14_class.proxy;

/**
 * @author chen
 * @date 2020/9/28 23:39
 * @Description
 */
public class RealObject implements Interface {
    @Override
    public void doSomething() {
        System.out.println("原始对象 doSomething：just do what  you want to do!!!");
    }

    @Override
    public void dosomethingElse(String arg) {
        System.out.println("原始对象 dosomethingElse：you can also do something else-->" + arg);
    }
}
