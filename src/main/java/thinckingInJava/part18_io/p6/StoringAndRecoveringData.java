package thinckingInJava.part18_io.p6;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author chen
 * @date 2020/12/19 23:08
 */
@Slf4j
public class StoringAndRecoveringData {
    public static void main(String[] args) throws IOException {
        // 使用 DataOutputStream 进行相应类型数据的输出
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("D:\\test")));
        out.writeDouble(5.34d);
        out.writeBoolean(true);
        out.writeChar('a');
        out.writeUTF("这是utf编码的字符串");
        out.writeInt(239);
        out.close();
        // 使用 DataInputStream 进行相应类型数据的读取
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("D:\\test")));
        /**
         *  利用语义化API进行写入和读取虽然很方便直观，但是有严格的格式顺序要求，否则读取的数据将极其不准确
         */
        log.info("读取的double值：{}", in.readDouble());
        log.info("读取的布尔值：{}", in.readBoolean());
        log.info("读取的char值：{}", in.readChar());
        log.info("读取的字符串值：{}", in.readUTF());
        log.info("读取的int 值：{}", in.readInt());

    }
}
