package geek.designer.proxy;

import sun.misc.ProxyGenerator;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chen
 * @date 2021/7/4 16:42
 * 为什么JDK动态代理只能代理接口？
 */
public class JDKProxyTest {
    private static final String PATH = System.getProperty("user.dir") + "/extra/class/chen/";
    private static final String proxyClassNamePrefix = "$Proxy";
    private static final AtomicLong nextUniqueNumber = new AtomicLong();

    public static void main(String[] args) {
        /**
         *   根据以下Proxy.newProxyInstance代码，跟踪源码，发现先通过{@link Proxy#getProxyClass0(java.lang.ClassLoader, java.lang.Class[])}获取代理类的Class对象
         *   实际上还是通过{@link Proxy.ProxyClassFactory#apply(java.lang.ClassLoader, java.lang.Class[])}方法中的 {@link sun.misc.ProxyGenerator#generateProxyClass(java.lang.String, java.lang.Class[], int)}生成了代理对象的字节码
         *   继续跟踪，发现默认情况下，字节码是直接加载到虚拟机内存，并不会保存下来，通过设置系统参数【sun.misc.ProxyGenerator.saveGeneratedFiles】为true就可以保存
         *   保存后可以生成的代理类已经给了你答案,其类声明为：public final class $Proxy0 extends Proxy implements Interface
         */
//        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
//        Proxy.newProxyInstance(Interface.class.getClassLoader(), new Class[]{Interface.class}, new DynamicProxyHandler(new RealObject()));


        // 了解原理后，其实可以自己生成指定接口的代理对象的字节码文件并保存下来
        long num = nextUniqueNumber.getAndIncrement();
        byte[] classBytes = ProxyGenerator.generateProxyClass(proxyClassNamePrefix + num, new Class[]{Interface.class});
//        FileUtil.writeBytes(classBytes, PATH + proxyClassNamePrefix + num+".class");
        try (FileOutputStream fio = new FileOutputStream(PATH + proxyClassNamePrefix + num + ".class", false);
             BufferedOutputStream bos = new BufferedOutputStream(fio)) {
            bos.write(classBytes, 0, classBytes.length);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
