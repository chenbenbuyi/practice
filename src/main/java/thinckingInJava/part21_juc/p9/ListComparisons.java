package thinckingInJava.part21_juc.p9;

import thinckingInJava.part17_collection.p2.CountingIntegerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author chen
 * @date 2020/9/4 23:09
 * @Description
 */
abstract class ListTest extends Tester<List<Integer>> {
    public ListTest(String testId, int nReaders, int nWriters) {
        super(testId, nReaders, nWriters);
    }

    class Reader extends TestTask {
        long result = 0;

        @Override
        void test() {
            for (int i = 0; i < testCycles; i++) {
                for (int index = 0; index < containerSize; index++) {
                    result += testContainer.get(index);
                }
            }
        }

        @Override
        void putResults() {
            readResult += result;
            readTime += duration;
        }
    }

    class Writer extends TestTask {

        @Override
        void test() {
            for (int i = 0; i < testCycles; i++) {
                for (int index = 0; index < containerSize; index++) {
                    testContainer.set(index, writeData[index]);
                }
            }
        }

        @Override
        void putResults() {
            writeTime += duration;
        }
    }

    @Override
    void startReadersAndWriters() {
        for (int i = 0; i < nReaders; i++) {
            exec.execute(new Reader());
        }

        for (int i = 0; i < nWriters; i++) {
            exec.execute(new Writer());
        }
    }
}

class SynchronizedArrayListTest extends ListTest {
    // 初始化一个同步容器
    List<Integer> containerInitializer() {
        return Collections.synchronizedList(new ArrayList<>(new CountingIntegerList(containerSize)));
    }

    public SynchronizedArrayListTest(int nReaders, int nWriters) {
        super("Sync 的 ArrayList", nReaders, nWriters);
    }
}

class CopyOnWriteArrayListTest extends ListTest {

    // 初始化一个同步容器
    List<Integer> containerInitializer() {
        return new CopyOnWriteArrayList<>(new CountingIntegerList(containerSize));
    }


    public CopyOnWriteArrayListTest(int nReaders, int nWriters) {
        super("CopyOnWriteArrayList", nReaders, nWriters);
    }
}

/**
 * 测试对比两种同步容器的性能差异
 * CopyOnWriteArray 是一种免锁容器，如果主要是读取任务，则比同步包装的SynchronizedArrayList 要快很多
 * 以下为取的最后一次的执行结果对比
 *
 Sync 的 ArrayList 10r 0w         3325488592              0

 Sync 的 ArrayList 9r 1w          3531766623      404857954   读、写时间累计： 3936624577

 Sync 的 ArrayList 5r 5w          1700704566     1541402486   读、写时间累计： 3242107052

 CopyOnWriteArrayList 10r 0w       56138707              0

 CopyOnWriteArrayList 9r 1w        90617119       35950220    读、写时间累计： 126567339

 CopyOnWriteArrayList 5r 5w        48089819     1210923411    读、写时间累计： 1259013230
 *
 *
 */
public class ListComparisons {
    public static void main(String[] args) {
        new SynchronizedArrayListTest(10, 0);
        new SynchronizedArrayListTest(9, 1);
        new SynchronizedArrayListTest(5, 5);
        new CopyOnWriteArrayListTest(10, 0);
        new CopyOnWriteArrayListTest(9, 1);
        new CopyOnWriteArrayListTest(5, 5);
        Tester.exec.shutdown();
    }
}
