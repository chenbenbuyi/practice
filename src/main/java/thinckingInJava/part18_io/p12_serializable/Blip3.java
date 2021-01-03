package thinckingInJava.part18_io.p12_serializable;

import java.io.*;

/**
 * @author chen
 * @date 2021/1/3 23:19
 * @Description
 */
public class Blip3 implements Externalizable {
    private int i;
    private String s;

    /**
     * 这里要注意的是，在反序列化的时候，调用的是默认构造器，所以不能在默认构造器中进行参数的初始化操作
     */
    public Blip3() {
//        i = 444;
//        s ="默认构造器";
        System.out.println("默认构造器Blip3");
    }

    public Blip3(int i, String s) {
        System.out.println("含参数的构造器");
        this.i = i;
        this.s = s;
    }

    @Override
    public String toString() {
        return "Blip3{" +
                "i=" + i +
                ", s='" + s + '\'' +
                '}';
    }

    /**
     *  对具体的字段进行手动序列化操作
     *  手动控制哪些参数需要序列化，哪些不需要序列化
     *  注意：序列化的参数顺序和反序列化的参数顺序是有关联的
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(s);
//        out.writeInt(i);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        s = ((String) in.readObject());
//        i = in.readInt();
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {

//        Blip3 blip = new Blip3();
        Blip3 blip = new Blip3(100, "随便写的字符串");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\test"));
             ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\test"))
        ) {
            // 对象序列化
            out.writeObject(blip);

            // 对象反序列化
            Blip3 blip3 = (Blip3) in.readObject();
            System.out.println(blip3);
        }
    }
}
