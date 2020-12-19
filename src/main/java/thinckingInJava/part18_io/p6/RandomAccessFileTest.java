package thinckingInJava.part18_io.p6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author chen
 * @date 2020/12/19 23:33
 * @Description
 */
public class RandomAccessFileTest {

    public static void main(String[] args) throws IOException {
        // rw 以读写方式打开一个RandomAccessFile
        RandomAccessFile rf = new RandomAccessFile("D:\\test", "rw");
        for (int i = 0; i <5 ; i++) {
            rf.writeDouble(i*5.14);
        }
        rf.writeUTF("文件已到末尾");
        rf.close();
        display();
        rf = new RandomAccessFile("D:\\test","rw");
        //seek ,设置文件指针偏移量(从0开始) 由于double 为8字节，所以下面表示查找偏移量为3的double值
        rf.seek(3*8);
        // 读取也会导致指针偏移量的位移
//        System.out.println("第四个双精度值为："+rf.readDouble());
        rf.writeDouble(6.666);
        rf.close();
        display();
    }

    static void display() throws IOException {
        RandomAccessFile rf = new RandomAccessFile("D:\\test", "r");
        for (int i = 0; i <5 ; i++) {
            System.out.println("value "+i+": "+rf.readDouble());
        }
        rf.readUTF();
        rf.close();
    }
}
