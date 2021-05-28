package jvm.classloader;

import cn.hutool.core.util.ClassLoaderUtil;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author chen
 * @date 2021/5/23 14:36
 * @Description 通过类加载器加载外部类文件进行方法调用
 */
public class ClassLoaderTest {

    /**
     *   直接从jar包中加载指定 的类执行目标方法
     */
    @Test
    public void test1() throws Exception{
        String jarPath = "file:C:\\Users\\Administrator\\Desktop\\test.jar";
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL(jarPath)});
        Class<?> clz = urlClassLoader.loadClass("chen.Helloadler");
        Object o = clz.newInstance();
        /**
         *  踩坑指南：类被加载了，方法名也正确却报找不到方法，那就是方法的访问控制问题，要声明为公共方法才能访问
         */
        Method method = clz.getMethod("sayHello",String.class);
        method.invoke(o,"你好范特西");
    }

    /**
     *   直接加载指定路径的类文件执行目标方法?????
     */
    @Test
    public void test2() throws Exception{
        Class<?> clz = ClassLoaderUtil.loadClass(new File("C:\\Users\\Administrator\\Desktop\\"), "chen.Helloadler");
        Object o = clz.newInstance();
        Method method = clz.getMethod("sayHello",String.class);
        method.invoke(o,"你好范特西");
    }


    /**
     *   为了防止类被直接反编译，修改了类字节码的后缀名，导致无法直接通过类名的形式进行加载
     *   直接加载文件夹下目标文件的形式
     */
    @Test
    public void test3() throws Exception{
        MyClassLoader myClassLoader = new MyClassLoader("C:\\Users\\Administrator\\Desktop\\test",".chen");
        Class<?> clz = myClassLoader.findClass("chen.Helloadler");
        Object o = clz.newInstance();
        Method method = clz.getMethod("sayHello",String.class);
        method.invoke(o,"你好范特西");
    }

    /**
     *   为了防止类被直接反编译，修改了类字节码的后缀名，导致无法直接通过类名的形式进行加载
     *   加载jar包中的目标文件的方式
     */
    @Test
    public void test4() throws Exception{
        MyJarClassLoader jarClassLoader = new MyJarClassLoader("C:\\Users\\Administrator\\Desktop\\test.jar",".chen");
        Class<?> clz = jarClassLoader.findClass("chen.Helloadler");
        Object o = clz.newInstance();
        Method method = clz.getMethod("sayHello",String.class);
        method.invoke(o,"你好范特西");
    }
}

