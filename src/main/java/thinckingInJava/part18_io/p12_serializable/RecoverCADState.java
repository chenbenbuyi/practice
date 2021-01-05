package thinckingInJava.part18_io.p12_serializable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * @author chen
 * @date 2021/1/5 0:44
 * @Description
 */
public class RecoverCADState {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\test"));
        // 这句代码多余吗？ 不多余，因为序列化和反序列化的信息读取顺序必须要有保证
//        in.readObject();
        Line.deserializeStaticState(in);
        List<Shape> shapes = (List<Shape>) in.readObject();
        System.out.println(shapes);
    }
}
