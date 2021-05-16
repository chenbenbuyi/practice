package jvm.memory.method;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/5/16 12:14
 * @Description 通过CGLib字节码技术动态生成类的方式构造方法区的溢出
 * 测试参数：-XX:MetaspaceSize=100m -XX:MaxMetaspaceSize=100m
 *
 * 延伸：​ CGLIB，代理的是类，不需要业务类继承接口，通过派生的子类来实现代理。通过在运行时，动态修改字节码达到修改类的目的
 */
public class MethodOOMTest {

    public static void main(String[] args) throws InterruptedException {
        int count = 0;
        try {
            TimeUnit.SECONDS.sleep(10);
            while (true) {
                count++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(Obj.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o, objects);
                    }
                });
                enhancer.create();
            }
        }finally {
            System.out.println(count);
        }
    }

    static class Obj {

    }
}
