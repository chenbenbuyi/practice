package thinckingInJava.part14_class.proxy;

/**
 * @author chen
 * @date 2020/9/28 23:42
 * @Description 静态代理对象
 */
public class StaticProxyObject implements Interface {

    private Interface anInterface;

    public StaticProxyObject(Interface anInterface) {
        this.anInterface = anInterface;
    }

    @Override
    public void doSomething() {
        System.out.println("doSomething：在原代理对象基础上进行额外操作,比如aop功能切面，通过代理切入额外的执行逻辑");
        anInterface.doSomething();
    }

    @Override
    public void dosomethingElse(String arg) {
        System.out.println("dosomethingElse：在原代理对象基础上进行额外操作："+arg);
        anInterface.dosomethingElse(arg);
    }
}

class ProxyText {
    public static void main(String[] args) {
        proxy(new StaticProxyObject(new RealObject()));
    }

    public static void proxy(Interface iface) {
        iface.doSomething();
        iface.dosomethingElse("骚情的浪起来");
    }
}
