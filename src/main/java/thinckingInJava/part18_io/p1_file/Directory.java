package thinckingInJava.part18_io.p1_file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author chen
 * @date 2020/12/15 22:48
 * @Description 递归输出指定目录下的所有文件的全路径
 */
public class Directory {
    public static File[] local(File dir, String regex) {
        return dir.listFiles(new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);

            @Override
            public boolean accept(File dir, String name) {
                return pattern.matcher(name).matches();
            }
        });
    }

    public static File[] local(String path, String regex) {
        return local(new File(path), regex);
    }

    public static class TreeInfo implements Iterable<File> {

        private List<File> files = new ArrayList<>();
        private List<File> dirs = new ArrayList<>();

        @Override
        public Iterator<File> iterator() {
            return files.iterator();
        }

        void addAll(TreeInfo info) {
            files.addAll(info.files);
            dirs.addAll(info.dirs);
        }

        @Override
        public String toString() {
            return "目录：" + PPrint.pformat(dirs) + "\n\n文件:" + PPrint.pformat(files);
        }

    }

    public static TreeInfo walk(String start, String regex) {
        return recurseDirs(new File(start), regex);
    }

    public static TreeInfo walk(File start, String regex) {
        return recurseDirs(start, regex);
    }

    public static TreeInfo walk(File start) {
        return recurseDirs(start, ".*");
    }

    public static TreeInfo walk(String start) {
        return recurseDirs(new File(start), ".*");
    }

    /**
     *  对目录文件进行递归
     */
    static TreeInfo recurseDirs(File startDir, String regex) {
        TreeInfo files = new TreeInfo();
        // listFiles() 只会list下一级，不会递归的list所有文件
        for (File file : startDir.listFiles()) {
            if (file.isDirectory()) {
                files.dirs.add(file);
                files.addAll(recurseDirs(file, regex));
            } else {
                if (file.getName().matches(regex)) {
                    files.files.add(file);
                }
            }
        }
        return files;
    }


    public static void main(String[] args) {
        TreeInfo info = walk("D:\\学习资料\\MongoDB");
        System.out.println(info);
    }
}
