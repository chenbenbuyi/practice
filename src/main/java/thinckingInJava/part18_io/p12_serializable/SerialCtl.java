package thinckingInJava.part18_io.p12_serializable;

import java.io.*;

/**
 * @author chen
 * @date 2021/1/3 23:45
 * @Description
 */
public class SerialCtl implements Serializable {
    private String a;
    // transient 关键字只能和Serializable对象一起使用，因为Externalizable接口对象需要手动进行字段的序列化，在默认情况下所有的字段都是transient的，不会进行自动的序列化机制
    private transient String b;

    public SerialCtl(String a, String b) {
        this.a = "非transient字段a的值：" + a;
        this.b = "transient字段b的值：" + b;
    }

    @Override
    public String toString() {
        return "SerialCtl{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                '}';
    }

    /**
     *  在不实现 Externalizable 子接口的前提下，可以添加（不是覆写）如下两个名为
     *      writeObject和readObject 的方法进行序列化和反序列化的相关自定义操作
     *   这里隐藏的信息是：
     *      ObjectOutputStream.writeObject调用时，会检查Serializable 对象是否自己实现有writeObject方法（反序列化同理），如果代码中主动提供了 writeObject和readObject 两个方法，则默认的序列化机制将不起作用，而是会在序列化和反序列化的过程中分别调用这两个方法
     *    writeObject和readObject  都是私有方法，如下示例可以在方法中调用默认的defaultWriteObject或defaultReadObject来执行默认的序列化机制
     *
     */
    private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
        out.defaultWriteObject();
        out.writeObject(b);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        b = ((String) in.readObject());
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SerialCtl serialCtl = new SerialCtl("1a", "2b");
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(buf);
        out.writeObject(serialCtl);

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buf.toByteArray()));
        SerialCtl sc = (SerialCtl) in.readObject();
        System.out.println(sc);
    }
}
