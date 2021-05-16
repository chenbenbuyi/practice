package jvm.memory.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @date 2021/5/15 8:32
 * @Description 测试堆内存溢出（尝试修改jdk版本比较不同）
 * 虚拟机参数设置：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 获取到堆转储文件后可以通过VisualVM等工具进行文件分析
 */
public class HeapOOM {

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        for (; ; ) {
            list.add(new OOMObject());
        }
    }

    static class OOMObject {

    }
}
