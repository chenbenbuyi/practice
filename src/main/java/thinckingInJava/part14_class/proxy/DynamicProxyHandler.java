package thinckingInJava.part14_class.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author chen
 * @date 2020/9/28 23:52
 * @Description 动态代理
 */
public class DynamicProxyHandler implements InvocationHandler {

    private Object proxied;

    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理类型：" + proxy.getClass() + ",执行方法：" + method + ",传入参数：" + Arrays.toString(args));
        return method.invoke(proxied, args);
    }
}

class DynamicProxy {
    public static void dynamicPxoy(Interface iface) {
        iface.doSomething();
        iface.dosomethingElse("动态骚情，依然很浪！");
    }

    public static void main(String[] args) {
        RealObject realObject = new RealObject();
        /**
         *  通过 静态方法 Proxy.newProxyInstance 创建动态的创建代理对象，参数需要：
         *  ① 类加载器
         *  ② 代理实现的接口列表（不是类或抽象类，而是接口） 这里只实现了 Interface 接口
         *  ③ InvocationHandler 接口的实例
         */
        Interface iface = (Interface) Proxy.newProxyInstance(Interface.class.getClassLoader(), new Class[]{Interface.class}, new DynamicProxyHandler(realObject));
        dynamicPxoy(iface);
    }
}
