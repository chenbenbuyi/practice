
package thinckingInJava.part18_io.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * @author chen
 * @date 2020/12/20 0:02
 * @Description
 */
public class TextFile extends ArrayList<String> {

    /**
     *   该构造器中，利用super 将读取的额数据按照指定的分隔符分割后存入ArrayList
     */
    public TextFile(String fileName, String splitter) {
        super(Arrays.asList(read(fileName).split(splitter)));
        if ("".equals(get(0)))
            remove(0);
    }

    public TextFile(String fileName) {
        this(fileName, "\n");
    }

    public void write(String fileName) {
        try (PrintWriter writer = new PrintWriter(new File(fileName).getAbsoluteFile())) {
            /**
             *  狗仔该对象（this）的时候，已经包含了要输出到文件的内容
             */
            for (String s : this) {
                writer.println(s);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()))) {
            String s;
            while ((s = reader.readLine()) != null) {
                sb.append(s).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public static void write(String fileName, String text) {
        try (PrintWriter writer = new PrintWriter(new File(fileName).getAbsoluteFile())) {
            writer.print(text);
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        String javaFile = "D:\\WorkSpace\\xiaoyuan\\src\\main\\java\\thinckingInJava\\part18_io\\util\\TextFile.java";
        String fileContent = read(javaFile);
        write("D:\\test", fileContent);
        TextFile textFile = new TextFile("D:\\test");
        textFile.write("D:\\test2");
        System.out.println();
    }

}
