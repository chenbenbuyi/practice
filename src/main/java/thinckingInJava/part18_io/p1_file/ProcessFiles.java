package thinckingInJava.part18_io.p1_file;

import java.io.File;
import java.io.IOException;

/**
 * @author chen
 * @Description 根据文件扩展名进行过滤
 */
public class ProcessFiles {
    public interface Strategy {
        void process(File file);
    }

    private Strategy strategy;
    private String extName;

    public ProcessFiles(Strategy strategy, String extName) {
        this.strategy = strategy;
        this.extName = extName;
    }

    public void start(String[] args) {
        try {
            if (args.length == 0)
                processDirectoryTree(new File("."));
            else
                for (String arg : args) {
                    File file = new File(arg);
                    if (file.isDirectory())
                        processDirectoryTree(file);
                    else {
                        if (arg.endsWith("." + extName))
                            arg += "." + extName;
                        strategy.process(new File(arg).getCanonicalFile());
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void processDirectoryTree(File root) throws IOException {
        for (File file : Directory.walk(root.getAbsolutePath(), ".*\\." + extName)) {
            // getCanonicalFile 返回抽象路径的规范形式
            strategy.process(file.getCanonicalFile());
        }
    }

    public static void main(String[] args) {
        new ProcessFiles(System.out::println, "java").start(args);
    }

}
