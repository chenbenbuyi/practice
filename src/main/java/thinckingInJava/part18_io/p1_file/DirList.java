package thinckingInJava.part18_io.p1_file;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;

/**
 * @author chen
 * @date 2020/12/15 22:32
 * @Description 文件过滤
 */
@Slf4j
public class DirList {
    public static void main(String[] args) {
        File file = new File("D:\\WorkSpace\\xiaoyuan");
        // list 会枚举出当前目录下所有的文件(包括目录、文件以及隐藏文件等)，但不会往下一级继续深入列举
        String[] list = file.list();
        String fileName = Arrays.toString(list);
        log.info("目录下的所有文件：{}", fileName);
        File[] files = file.listFiles();
        log.info("目录下的所有文件2：{}", Arrays.toString(files));
        /**
         *  通过 FilenameFilter 接口进行条件筛选
         *  类似的，File.listFiles(FileFilter filter) 可以以文件对象的形式而不是字符串名的形式过滤
         */
        String[] filterList = file.list((dir, name) -> name.endsWith(".git"));
        log.info("目录下的所有文件过滤之后：{}", Arrays.toString(filterList));
    }
}
