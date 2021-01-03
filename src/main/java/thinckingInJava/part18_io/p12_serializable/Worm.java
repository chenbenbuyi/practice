package thinckingInJava.part18_io.p12_serializable;

import java.io.*;
import java.util.Random;

/**
 * @author chen
 * @date 2021/1/3 10:17
 * @Description 对象序列化和反序列化测试
 */
public class Worm implements Serializable {
    private static Random rand = new Random(47);
    private Data[] ds = {
            new Data(rand.nextInt(10)),
            new Data(rand.nextInt(10)),
            new Data(rand.nextInt(10))
    };
    private Worm next;
    private char c;

    public Worm(int i, char x) {
        System.out.println("Worm 控制器：" + i);
        if (--i > 0) {
            next = new Worm(i, (char) (x + 1));
        }
    }

    public Worm() {
        System.out.println("默认构造器");
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(":");
        result.append(c).append("(");
        for (Data d : ds) {
            result.append(d);
        }
        result.append(")");
        if (next != null) {
            result.append(next);
        }
        return result.toString();
    }

    /**
     *  无论对象的构造过程多么复杂，实际的序列化和反序列化过程却非常简单
     *  输出打印中你会发现：
     *      ① 对一个对象进行反序列化的过程中，不会调用任何构造器，对象都是从流中（文件流会其它流甚至包括网络传输）进行恢复
     *      ② 对象序列化并不仅仅限于将对象信息写到文件，你可以将对象数据写入其它的流等等
     *
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Worm worm = new Worm(6, 'a');
        System.out.println("原始对象 worm=" + worm + ",哈希值:" + worm.hashCode());
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\test"));
             ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\test"))
        ) {
            // 序列化到文件
            out.writeObject("将对象序列化到文件\n");
            out.writeObject(worm);
            // 反序列化
            String msg = (String) in.readObject();
            Worm w = (Worm) in.readObject();
            System.out.println(msg + "从文件中反序列化 worm=" + w + ",哈希值:" + w.hashCode());

            System.out.println("======================================");
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out2 = new ObjectOutputStream(bout);
            out2.writeObject("将对象序列化到字节数组的输出流中\n");
            out2.writeObject(worm);
            out2.flush();

            ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
            ObjectInputStream in2 = new ObjectInputStream(bin);
            // 这里的反序列化顺序也是很重要的
            String s = (String) in2.readObject();
            Worm w2 = (Worm) in2.readObject();
            System.out.println(s + "从流中反序列化 worm=" + w2 + ",哈希值:" + w2.hashCode());

            /**
             *  演示对象的反序列化需要找到相应的.class文件
             *  NotFoundClass 先序列化到文件，然后删除 NotFoundClass 类，再进行反序列化尝试
             *  实验表明：即使不进行强转，只要虚拟机找不到反序列化时相应对象的类，就会抛出
             *      java.lang.ClassNotFoundException
             */
//            ObjectOutputStream out3 = new ObjectOutputStream(new FileOutputStream("D:\\test2"));
//            out3.writeObject(new NotFoundClass());

            ObjectInputStream in3 = new ObjectInputStream(new FileInputStream("D:\\test2"));
            Object object = in3.readObject();
//            NotFoundClass notFoundClass = ((NotFoundClass) object);
        }

    }
}

class Data implements Serializable {
    private int n;

    public Data(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "Data{" +
                "n=" + n +
                '}';
    }
}

//class NotFoundClass implements Serializable{}
