package thinckingInJava.part18_io.p8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author chen
 * @date 2020/12/21 22:38
 * @Description 类似回声测试，输入什么打印什么
 *  标注I/O 模型中，Java 主要提供了 System.in(输入设备是键盘) System.out（输出设备是控制台） System.err 三类
 *  其中，  System.out System.err 都是PrintStream ，也即被包装过的 字节流对象
 *  System.in 则是原生的 InputStream 接口，需要自己进行包装使用，比如下面示例中转成字符流
 */
public class Echo {
    public static void main(String[] args) throws IOException {
        /**
         * InputStreamReader 适配器，将字节流InputStream 转换成字符流 Reader
         * 同理有 OutputStreamWriter
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s=reader.readLine())!=null&&s.length()!=0){
            System.out.println(s);
        }
    }
}
