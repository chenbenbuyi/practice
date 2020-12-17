package thinckingInJava.part18_io.p6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author chen
 * @date 2020/12/17 23:41
 * @Description 缓冲读取文件内容
 */
public class BufferedReadTest {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("D:\\WorkSpace\\xiaoyuan\\pom.xml");
        StringBuilder sb;
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String str;
            sb = new StringBuilder();
            while ((str = reader.readLine()) != null) {
                // 由于readLine 读取的行是不包含换行符的，所以如果要格式输出，这里需要手动拼接换行符
                sb.append(str).append("\n");
            }
            System.out.println(sb.toString());
        }

        /**
         *  从读取的数据字符串（内存）中安字符读取
         *  StringReader.read 以 int 形式返回下一个字符
         */
//        StringReader stringReader = new StringReader(sb.toString());
        StringReader stringReader = new StringReader("陈本布衣");
        int c;
        while ((c=stringReader.read())!=-1){
            System.out.println((char)c);
        }
    }
}
