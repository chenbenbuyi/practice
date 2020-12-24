package thinckingInJava.part18_io.p1_file;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @date: 2020/12/24 9:14
 * @author: chen
 * @desc: 文件读取
 */
public class FileReadTest {
    public static void main(String[] args) throws IOException {
        String path = "D:\\test";
        readByFiles(path);
    }

    /**
     * 方式一
     * 通过 commons.io 提供的迭代器模式的api读取文件
     * 底层也是基于缓冲的BufferedReader 进行字符读取
     * 用工具方法的好处是，方法调用简洁高效，不用自己处理流的开启和关闭等细节
     */
    public static void readByLineIterator(String path) throws IOException {
        LineIterator lineIterator = IOUtils.lineIterator(new FileInputStream(path), Charsets.UTF_8);
        while (lineIterator.hasNext()) {
            String line = lineIterator.nextLine();
            System.out.println(line);
        }
    }


    /**
     * 方式二 利用jdk 8的工具方法
     * 通过 commons.io 提供的迭代器模式的api读取文件
     * 底层也是基于缓冲的BufferedReader 进行字符读取
     */
    public static void readByFiles(String path) throws IOException {
        try(Stream<String> lines = Files.lines(Paths.get(path), Charsets.UTF_8)){
            lines.forEach(line -> {
                System.out.println(line);
            });
        }
    }


}
