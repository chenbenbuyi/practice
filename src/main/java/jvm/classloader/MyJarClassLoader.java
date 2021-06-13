package jvm.classloader;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.SecureClassLoader;

/**
 * @author chen
 * @date 2021/5/23 15:35
 * @Description
 */
public class MyJarClassLoader extends SecureClassLoader {

    private String jarPath;
    private String suffix;

    MyJarClassLoader(String jarPath, String suffix) {
        this.jarPath = jarPath;
        this.suffix = suffix;
    }

    /**
     * 在 {@link ClassLoaderTest#test4()}的测试情形中，如果当前测试环境的类路径下有同名类，根据双亲委派模型，优先加载的就是类路径下的目标类，而不是导出jar包中的目标类
     * 通过重写loadClass方法，打破双亲委派模型——优先从指定路径找，找不到再走双亲委派机制
     * <p>
     * 异常启示录：IORuntimeException: FileNotFoundException: JAR entry java/lang/Object.class not found in xxx.
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                // 打破双亲委派，如果包名包含 chen ，则先走自定义的类加载机制，否则向上委托，走双亲委派机制。意思很明了，不过是硬编码的方式
//                if(name.contains("chen"))
//                    c = this.findClass(name);
//                else
//                    c = super.loadClass(name);
                // 优先从本地加载，加载不到再走双亲委派
                c = findClass(name);
                if (c == null) {
                    c = super.loadClass(name);
                }
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String name) {
        // 这里的分隔符必须是 "/"， 用 File.separator 会导致无法成功加载jar包中的目标类
        byte[] bytes = readBytes("jar:file:\\" + jarPath + "!/" + name.replace(".", "/").concat(suffix));
        if (ArrayUtil.isAllEmpty(bytes))
            return null;
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] readBytes(String path) {
        InputStream in = null;
        byte[] bytes = null;
        try {
            URL url = new URL(path);
            in = url.openStream();
            int len = in.available();
            bytes = new byte[len];
            int readLength = in.read(bytes);
            if ((long) readLength < len) {
                throw new IOException(StrUtil.format("File length is [{}] but read [{}]!", len, readLength));
            }
        } catch (Exception var10) {
//            System.err.println(var10.getMessage());
        } finally {
            IoUtil.close(in);
        }
        return bytes;
    }
}
