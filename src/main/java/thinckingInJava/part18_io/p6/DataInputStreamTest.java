package thinckingInJava.part18_io.p6;

import java.io.*;

/**
 * @author chen
 * @date 2020/12/18 0:07
 * @Description
 */
public class DataInputStreamTest {
    public static void main(String[] args) throws IOException {
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream("sdfsf".getBytes()));
        /**
         * EOFException - 如果此输入流已到达结束。
         *  这里有点特殊的在于，去读取结束是采用抛出异常，而不是返回如-1之类的值
         *  readByte() 读取下一个字节为的数据
         *  readChar() 读取下两个字节，解释为char
         *  readFloat / readDouble () 类推
         *  readFully（byte[] b） 读取参数字节数组的字节数
         */
//        while (true){
//            System.out.println((char) inputStream.readByte());
//        }

        DataInputStream dataInputStream =
                new DataInputStream(new BufferedInputStream(new FileInputStream("D:\\WorkSpace\\xiaoyuan\\pom.xml")));
        while (dataInputStream.available() != 0) {
            System.out.println((char) dataInputStream.readByte());
        }
    }
}
