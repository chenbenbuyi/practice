package jvm.classloader;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureClassLoader;

/**
 * @author chen
 * @date 2021/5/23 15:35
 * @Description
 */
public class MyClassLoader extends SecureClassLoader {

    private String classPath;
    private String suffix;

    MyClassLoader(String classPath,String suffix) {
        this.classPath = classPath;
        this.suffix = suffix;
    }

    @Override
    protected Class<?> findClass(String name) {
        System.out.println("MyJarClassLoader 自定义的findClass方法。。。。。。。。。");
        byte[] bytes = loadClassData(classPath + File.separator + name.replace(".", File.separator).concat(suffix));
        return defineClass(name, bytes, 0, bytes.length);
    }


    /**
     * 来源于ClassLoader 类注释启发，通过获取网络传输或文件中类字节码的字节数组形式来实现非标准class文件的类加载
     */
    private byte[] loadClassData(String classPath) {
        return readBytes(classPath);
    }


    private byte[] readBytes(String path) {
        File file = new File(path);
        long len = file.length();
        byte[] bytes = new byte[(int) len];
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            int readLength = in.read(bytes);
            if ((long) readLength < len) {
                throw new IOException(StrUtil.format("File length is [{}] but read [{}]!", len, readLength));
            }
        } catch (Exception var10) {
            throw new IORuntimeException(var10);
        } finally {
            IoUtil.close(in);
        }
        return bytes;
    }
}
