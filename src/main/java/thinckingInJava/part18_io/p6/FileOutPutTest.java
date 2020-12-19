package thinckingInJava.part18_io.p6;

import java.io.*;

/**
 * @author chen
 * @date 2020/12/18 23:35
 * @Description 文件输出
 */
public class FileOutPutTest {
    public static void main(String[] args) throws IOException {
        /**
         * StringReader 一个读取字符串的字符流
         */
        BufferedReader reader = new BufferedReader(new StringReader("博客园，陈本布衣\n换行以后\n这是第三行了"));
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("D:\\test")));
        // 实际上，对于 PrintWriter，你不需要自己去包装流，可以直接传入文件名或文件对象，由api底层进行自动包装
        PrintWriter shortcut = new PrintWriter("D:\\test");
        int lineNumber = 1;
        String s;
        while ((s = reader.readLine()) != null) {
            shortcut.write(lineNumber++ + ": " + s+"\n");
        }
        // 如果不显示的关闭流，缓冲区的内容不会被清空和刷新，输出的内容很可能不完整
        printWriter.close();
        shortcut.close();
    }
}
