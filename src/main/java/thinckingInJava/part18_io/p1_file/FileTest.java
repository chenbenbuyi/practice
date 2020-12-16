package thinckingInJava.part18_io.p1_file;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chen
 * @date 2020/12/16 22:20
 * @Description File 类基本操作
 */
@Slf4j
public class FileTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        String path = "D:\\test";
        File file = new File(path);
        /**
         * 文件字节大小
         *  ① 该方法只能获取文件大小，目录大小无法获取
         *  ② 注意 file.length() 返回值，long 也就表示想要获取当超过该类型最大值的文件时，获取的字节数不准确
         *   其它api同理
         */
        log.info("file 文件字节大小：{}", file.length());
        log.info("file 文件字节大小2：{}", new FileInputStream(file).available());
        log.info("file 文件字节大小3：{}", new FileInputStream(file).getChannel().size());
        // 文件最后修改时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("文件最后修改时间：{}", simpleDateFormat.format(new Date(file.lastModified())));
        /**
         * 文件重命名
         * 该方法的行为的许多方面固有地依赖于平台。
         * 建议使用独立于平台的重命名方式Files.move
         */
        boolean result = file.renameTo(new File("D:\\test2"));
        Files.move(Paths.get("D:\\chenbenbuyi"), Paths.get("D:\\test2"));
        log.info("重命名成功：{}", result);
    }
}
