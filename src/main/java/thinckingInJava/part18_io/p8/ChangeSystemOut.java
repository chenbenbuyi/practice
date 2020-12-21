package thinckingInJava.part18_io.p8;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author chen
 * @date 2020/12/21 22:49
 */
public class ChangeSystemOut {
    public static void main(String[] args) throws IOException {
//        PrintWriter writer = new PrintWriter(System.out);
        // 第二个参数为true,将会自动情况缓冲区 ，否则就要自己显示的关闭流
        PrintWriter writer = new PrintWriter(System.out,true);
        /**
         *  根据名字终于反应过来：PrintWriter 恰如其名，在Writer的基础上，多了很多print方法
         */
        writer.println("你好，陈本布衣！");
        // 流一定需要关闭，尤其带缓冲的，如果不关闭，你很可能什么也看不到。
//        writer.close();
    }
}
