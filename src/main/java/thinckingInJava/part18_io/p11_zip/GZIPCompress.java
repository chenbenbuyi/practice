package thinckingInJava.part18_io.p11_zip;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author chen
 * @date 2020/12/23 23:15
 * @Description 对单个文件进行压缩
 */
public class GZIPCompress {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("D:\\test"));
        BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream("D:\\chen.gz")));
        /**
         *  通过输入流读取原文件内容，通过输出流（包装了压缩处理的流）将内容写入压缩文件
         */
        int c;
        while ((c = in.read()) != -1) {
            out.write(c);
        }
        in.close();
        out.close();
        /**
         * 读取压缩文件内容
         * 注意，如果文件内容带有中文，实际读出来是有乱码的，因为原始压缩的过程中其实就已经乱码了
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream("D:\\chen.gz")),"UTF-8"));
        String s;
        while ((s=reader.readLine())!=null){
            System.out.println(s);
        }

    }
}
