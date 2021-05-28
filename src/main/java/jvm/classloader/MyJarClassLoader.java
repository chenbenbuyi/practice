package jvm.classloader;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.io.FileInputStream;
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

    @Override
    protected Class<?> findClass(String name) {
        byte[] bytes = readBytes("jar:file:" + jarPath + "!/" + name.replace(".", File.separator).concat(suffix));
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] readBytes(String path) {
        InputStream in = null;
        byte[] bytes;
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
            throw new IORuntimeException(var10);
        } finally {
            IoUtil.close(in);
        }
        return bytes;
    }
}
