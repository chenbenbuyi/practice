package thinckingInJava.part18_io.p12_serializable;

import java.io.*;

/**
 * @author chen
 * @date 2021/1/3 21:51
 * @Description
 *  通过继承 Serializable 的子接口 Externalizable 来对序列化和反序列化进行控制
 *   Externalizable 接口的两个方法 writeExternal 和 readExternal 分别会在序列化和反序列化的时候被调用
 */

public class Blips {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\test"));
             ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\test"))
        ) {
            Blip1 blip1 = new Blip1();
            Blip2 blip2 = new Blip2();
            // 对象序列化
            out.writeObject(blip1);
            out.writeObject(blip2);

            // 对象反序列化
            Blip1 blip11 = (Blip1) in.readObject();
            System.out.println(blip1);
//            Blip2 blip21 = (Blip2) in.readObject();
        }

    }
}

class Blip1 implements Externalizable {
    private String name ;

    public Blip1() {
        name ="Blip1";
        System.out.println("Blip1 类默认构造器");
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("B1 writeExternal");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("B1 readExternal");
    }

    @Override
    public String toString() {
        return "Blip1{" +
                "name='" + name + '\'' +
                '}';
    }
}

class Blip2 implements Externalizable {
    private String name = "Blip2";

    /**
     *  注意这里与 Blip1 的区别在于该默认构造器不是公共的，会导致反序列化的时候抛出 InvalidClassException 异常。在java doc 中，以下情形会导致该异常：
     *  ① 类的串行版本与从流中读取的类描述符的类型不匹配
     *  ② 该类包含未知的数据类型
     *  ③ 该类没有可访问的无参数构造函数。实际上，必须显示的写出默认构造器，虽然不写也有有隐式的默认构造器存在，但依然会抛出该异常
     */
    Blip2() {
        System.out.println("Blip2 类默认构造器");
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("B2 writeExternal");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("B2 readExternal");
    }

    @Override
    public String toString() {
        return "Blip2{" +
                "name='" + name + '\'' +
                '}';
    }
}
