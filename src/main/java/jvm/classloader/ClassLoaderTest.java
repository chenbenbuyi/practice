package jvm.classloader;

import cn.hutool.core.util.ClassLoaderUtil;
import jvm.classloader.spi.IHelloadler;
import org.junit.Test;
import sun.misc.Launcher;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author chen
 * @date 2021/5/23 14:36
 * @Description 通过类加载器加载外部类文件进行方法调用
 */
public class ClassLoaderTest {

    private static final String PATH = System.getProperty("user.dir") + File.separator + "extra" + File.separator;

    /**
     * 通过JDK自身的方法直接从jar包中加载指定 的类执行目标方法。
     * 踩坑指南：如果类被加载了，方法名也正确却报找不到方法，那就是方法的访问控制问题，要声明为公共方法才能访问
     */
    @Test
    public void test1() throws Exception {
        String jarPath = "file:" + PATH + "test.jar";
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL(jarPath)});
        Class<?> clz = urlClassLoader.loadClass("chen.Helloadler");
        Object o = clz.newInstance();
        inovke(o, clz);
    }

    /**
     * 通过第三方工具包加载类执行目标方法
     * Hutool 的 ClassLoaderUtil 针对jar包或目录都可以成功加载
     * 实测表明，加载的类文件需要满足类全限定名的包结构，直接放个字节码文件是无法完成类加载的
     */
    @Test
    public void test2() throws Exception {
//        Class<?> clz = ClassLoaderUtil.loadClass(new File(PATH + "test.jar"), "chen.Helloadler");
        Class<?> clz = ClassLoaderUtil.loadClass(new File(PATH + "class"), "chen.Helloadler");
        Object o = clz.newInstance();
        inovke(o, clz);
    }


    /**
     * 为了防止类被直接反编译，修改了类字节码的后缀名，导致无法直接通过类名的形式进行加载
     * 但是由于类文件的字节码内容并没有发生变化，所以可以通过输入类的二进制字节流的方式来加载类
     */
    @Test
    public void test3() throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader(PATH + "class", ".chen");
        Class<?> clz = myClassLoader.loadClass("chen.Helloadler");
        Object o = clz.newInstance();
        inovke(o, clz);
    }


    /**
     * 同样基于上述事实更进一步，不是从文件系统中找类文件，而是从jar包中加载
     */
    @Test
    public void test4() throws Exception {
        MyJarClassLoader myClassLoader = new MyJarClassLoader(PATH + "test.jar", ".chen");
        Class<?> clz = myClassLoader.loadClass("chen.Helloadler");
        Object o = clz.newInstance();
        inovke(o, clz);
    }


    /**
     * 上述示例都是通过反射进行方法调用，更优雅的方式是能够明确调用对象的类型，而且是基于接口。
     * 由于实现是在第三方jar包中进行的实现，因此只需定义本地接口抽象，然后基于spi加载第三方的实现进行调用
     * 实测表明：
     *      1、spi规范写死的目录为 classpath 下面的 META-INF/services/ 该目录既可以在本应用自己的classpath中定义，也可以再第三方jar包自己的classpath中定义，
     *          只要最终能通过jar包引用的方式（测试发现maven项目也能直接引入非maven包）置于整个应用的classpath下，都能完成对目标对象的调用
     *      2、本示例中，由于jar包是依靠自己的类加载器加载，并没有build path 添加的项目的类路径下，所以需要在项目内部兴建SPI的规范文件指定具体的实现类进行加载
     *      3、第三方jar包再实现spi的接口后，导包是不需要将spi接口一起导入jar包的，可以在导包的时候勾选去除
     *
     *  SPI 关键代码：
     *      {@link ServiceLoader.LazyIterator#hasNextService() }
     *      {@link ServiceLoader.LazyIterator#nextService()}
     *  针对Class.forName反射api的使用，大多数场景不需要指定类加载器，默认就是AppClassLoader
     */
    @Test
    public void testSPI()  {
        MyJarClassLoader myClassLoader = new MyJarClassLoader(PATH + "test.jar", ".class");
        /**
         * 默认的类加载器是上下文类加载器，依然是从类路径下加载，如果要根据自己的实际需求加载，就需要传入自定义的类加载器
         */
        ServiceLoader<IHelloadler> load = ServiceLoader.load(IHelloadler.class, myClassLoader);
        Iterator<IHelloadler> iterator = load.iterator();
        // 因为一个spi文件可以指定多个不同的实现
        while (iterator.hasNext()){
            IHelloadler helloadler = iterator.next();
            helloadler.sayHello("SPI");
            helloadler.relayHello("陈本布衣,SPI");
        }
    }

    /**
     *  Java程序启动时启动类加载器会创建Launcher实例作为程序入口
     *  Launcher实例首先会创建扩展类加载器，然后以该类加载器为上级创建应用程序类加载器（AppClassLoader）并将其设置为上下文类加载器（TCCL）
     *  上下文类加载用来破坏双亲委派加载机制，实现SPI等
     *  {@link Launcher#Launcher()}
     */
    @Test
    public void classloaderTest(){
        // Launcher 是由启动类加载器加载的
        ClassLoader classLoader = Launcher.class.getClassLoader();
        ClassLoader appClassLoader = Launcher.getLauncher().getClassLoader();
        ClassLoader extClassLoader = appClassLoader.getParent();
        System.out.println(classLoader);
        System.out.println(appClassLoader);
        System.out.println(extClassLoader);
    }

    private void inovke(Object o, Class<?> clz) throws Exception {
        Method method = clz.getMethod("sayHello", String.class);
        Method method2 = clz.getMethod("relayHello", String.class);
        method.invoke(o, "陈本布衣");
        method2.invoke(o, "陈本布衣,博客园");
    }

}

