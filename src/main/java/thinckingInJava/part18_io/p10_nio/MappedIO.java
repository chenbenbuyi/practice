package thinckingInJava.part18_io.p10_nio;

import java.io.*;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @author chen
 * @date 2020/12/31 0:53
 * @Description
 */
public class MappedIO {
    private static int numOfInts = 4000000;
    private static int numOfUbuffInts = 200000;

    /**
     *  基于模板方法的测试类，比较下文的实现，确实代码要比自己写的骚气
     */
    private abstract static class Tester {
        private String name;

        public Tester(String name) {
            this.name = name;
        }

        public void runTest() {
            System.out.print(name + ":");
            try {
                long start = System.nanoTime();
                test();
                double duration = System.nanoTime() - start;
                System.out.format("%.2f\n", duration / 1.0e9);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public abstract void test() throws IOException;
    }

    private static Tester[] tests = {
            new Tester("IO流的方式执行文件写入") {
                @Override
                public void test() throws IOException {
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("D:\\test"))));
                    for (int i = 0; i < numOfInts; i++) {
                        dos.writeInt(i);
                    }
                    dos.close();
                }
            },
            new Tester("内存映射的方式执行文件写入") {
                @Override
                public void test() throws IOException {
                    FileChannel fc = new RandomAccessFile("D:\\test", "rw").getChannel();
                    IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
                    for (int i = 0; i < numOfInts; i++) {
                        ib.put(i);
                    }
                    fc.close();
                }
            },
            new Tester("IO流的方式读文件") {
                @Override
                public void test() throws IOException {
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream("D:\\test")));
                    for (int i = 0; i < numOfInts; i++) {
                        dis.readInt();
                    }
                    dis.close();
                }
            },
            new Tester("内存文件映射只读模式读取文件") {
                @Override
                public void test() throws IOException {
                    FileChannel fc = new FileInputStream(new File("D:\\test")).getChannel();
                    IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).asIntBuffer();
                    while (ib.hasRemaining()) {
                        ib.get();
                    }
                    fc.close();
                }
            },
            new Tester("IO流的方式读/写") {
                @Override
                public void test() throws IOException {
                    RandomAccessFile fc = new RandomAccessFile(new File("D:\\test"), "rw");
                    fc.writeInt(1);
                    for (int i = 0; i < numOfUbuffInts; i++) {
                        fc.seek(fc.length() - 4);
                        fc.writeInt(fc.readInt());
                    }
                    fc.close();
                }
            },
            new Tester("内存文件映射的方式读/写") {
                @Override
                public void test() throws IOException {
                    FileChannel fc = new RandomAccessFile(new File("D:\\test"), "rw").getChannel();
                    IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
                    ib.put(0);
                    for (int i = 1; i < numOfUbuffInts; i++) {
                        ib.put(ib.get(i - 1));
                    }
                    fc.close();
                }
            }
    };

    public static void main(String[] args) {
        for (Tester test : tests) {
            test.runTest();
        }
    }
}
