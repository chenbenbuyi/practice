package thinckingInJava.part18_io.p12_serializable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @date 2021/1/4 21:37
 * @Description
 *  通过一个字节序列来使用对象序列化，然后在反序列化，从而实现对任何可序列化对象的深度拷贝（复制的是整个对象网，而不仅仅是基本类型数据和对象引用）
 */
public class MyWorld {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        House house = new House();
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal("狗狗", house));
        animals.add(new Animal("猫咪", house));
        animals.add(new Animal("鸭子", house));
        System.out.println("所有的动物:" + animals);
        ByteArrayOutputStream buf1 = new ByteArrayOutputStream();
        ObjectOutputStream out1 = new ObjectOutputStream(buf1);
        out1.writeObject(animals);
        out1.writeObject(animals);

        ByteArrayOutputStream buf2 = new ByteArrayOutputStream();
        ObjectOutputStream out2 = new ObjectOutputStream(buf2);
        out2.writeObject(animals);

        // 反序列化
        ObjectInputStream in1 = new ObjectInputStream(new ByteArrayInputStream(buf1.toByteArray()));
        ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(buf2.toByteArray()));

        List als1 = (List) in1.readObject();
        List als2 = (List) in1.readObject();
        List als3 = (List) in2.readObject();
        System.out.println(als1);
        System.out.println(als2);
        System.out.println(als3);

    }
}

class House implements Serializable {
}

class Animal implements Serializable {
    private String name;
    private House preferenceHouse;

    public Animal(String name, House preferenceHouse) {
        this.name = name;
        this.preferenceHouse = preferenceHouse;
    }

    @Override
    public String toString() {
        return name + "[" + super.toString() + "]," + preferenceHouse + "\n";
    }
}
