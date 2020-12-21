package thinckingInJava.part18_io.p8;

import java.io.*;

/**
 * @author chen
 * @date 2020/12/21 22:59
 * @Description 重定向输出
 * 所谓的重定向，是对标准输入（System.in-键盘输入）、输出（System.out-控制台）的重定向
 * 考虑控制台有大量输出而无法阅读的场景
 */
public class Redirecting {
    public static void main(String[] args) throws IOException {
        String filePath = "D:\\WorkSpace\\xiaoyuan\\src\\main\\java\\thinckingInJava\\part18_io\\p8\\Redirecting.java";
        PrintStream console = System.out;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));
        PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("D:\\test")));
        /**
         *  将标准输入重定向到文件，将标准输出和标准错误重定向到另一个文件。这里有点绕，理解如下：
         *   标准输入本来是键盘输入，但是这里重定向到文件，则表示是有文件输入，说白了就是从文件读取本该由键盘读取的数据
         *   但是紧接着的打印语句中控制台没有输出，因为标准输出被重定向到了指定的文件输出流中，所以实际上是打印到了文件中
         *   最后，将标注输出还原
         */
        System.setIn(in);
        System.setOut(out);
        System.setErr(out);
        System.out.println("我被重定向了，你猜这句话会输出到哪里，控制台还是文件？");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            /**
             *  抛出异常后代码中断，流无法关闭，文件看不到内容
             *  而  e.printStackTrace(); 跟踪源码，其实就是一个包装了的标准错误流
             */
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        out.close();
        // 还原
        System.out.println("还原之前，我该在哪里打印？");
        System.setOut(console);
        System.out.println("还原之后，我该在哪里打印？");
    }
}
