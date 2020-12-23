package thinckingInJava.part18_io.p11_zip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author chen
 * @date 2020/12/23 23:53
 * @Description 将源文件(或目录)进行压缩
 */
public class ZipCompress {
    public static void main(String[] args) throws IOException {
        String source = "D:\\hello";
        String target = "D:\\test.zip";
        File file = new File(source);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(target));
        BufferedOutputStream bos = new BufferedOutputStream(out);
        zip(file,file.getName(), out, bos);
        bos.close();
        out.close();
        System.out.println("目录文件压缩完成");
    }

    private static void zip(File source,String fileName, ZipOutputStream out, BufferedOutputStream bos) throws IOException {
        if (source.isDirectory()) {
            File[] files = source.listFiles();
            if (files.length == 0) {
                out.putNextEntry(new ZipEntry(fileName + "/"));
            }
            for (File file : files) {
                /**
                 * 如果要保持原目录的层次结构，这里文件名的构造就很重要
                 */
                zip(file,fileName+"/"+ file.getName(), out, bos);
            }
        } else {
            out.putNextEntry(new ZipEntry(fileName));
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
            byte[] bytes = new byte[1024];
            int len;
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            bis.close();
        }
    }
}
