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
        String file = read("TextFile.java");
        write("D:\\test", file);
        TextFile textFile = new TextFile("D:\\test");
        textFile.write("D:\\test2");
        TreeSet<String> words = new TreeSet<>(
                new TextFile("TextFile.java", "\\w+")
        );
        System.out.println(words.headSet("a"));
    }

}
