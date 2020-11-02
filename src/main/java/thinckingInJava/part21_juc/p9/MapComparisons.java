package thinckingInJava.part21_juc.p9;

import thinckingInJava.part16.p6.CountingGenerator;
import thinckingInJava.part17.p2.MapData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chen
 * @date 2020/9/6 10:28
 * @Description
 *
SynchronizedHashMap  10r 0w     4891117788              0

SynchronizedHashMap  9r 1w      4447914619      417582048 读、写时间累计：4865496667

SynchronizedHashMap  5r 5w      2870143557     2434720440 读、写时间累计：5304863997

ConcurrentHashMap  10r 0w        201943031              0

ConcurrentHashMap  9r 1w         213422995       26385721 读、写时间累计：239808716

ConcurrentHashMap  5r 5w         177116340      572839106 读、写时间累计：749955446
 */
public class MapComparisons {
    public static void main(String[] args) {
        new SynchronizedHashMapTest(10, 0);
        new SynchronizedHashMapTest(9, 1);
        new SynchronizedHashMapTest(5, 5);
        new ConcurrentHashMapTest(10, 0);
        new ConcurrentHashMapTest(9, 1);
        new ConcurrentHashMapTest(5, 5);
        Tester.exec.shutdown();
    }
}

abstract class MapTest extends Tester<Map<Integer, Integer>> {

    public MapTest(String testId, int nReaders, int nWriters) {
        super(testId, nReaders, nWriters);
    }

    class Reader extends TestTask {
        long result = 0;

        void test() {
            for (int i = 0; i < testCycles; i++) {
                for (int index = 0; index < containerSize; index++) {
                    result += testContainer.get(index);
                }
            }
        }

        void putResults() {
            readResult += result;
            readTime += duration;
        }
    }

    class Writer extends TestTask {
        void test() {
            for (int i = 0; i < testCycles; i++) {
                for (int index = 0; index < containerSize; index++) {
                    testContainer.put(index, writeData[index]);
                }
            }
        }

        void putResults() {
            writeTime += duration;
        }
    }

    void startReadersAndWriters() {
        for (int i = 0; i < nReaders; i++) {
            exec.execute(new Reader());
        }
        for (int i = 0; i < nWriters; i++) {
            exec.execute(new Writer());
        }
    }
}

class SynchronizedHashMapTest extends MapTest {
    @Override
    Map<Integer, Integer> containerInitializer() {
        return Collections.synchronizedMap(new HashMap<>(MapData.map(
                new CountingGenerator.Integer(),
                new CountingGenerator.Integer(),
                containerSize)));
    }

    public SynchronizedHashMapTest(int nReaders, int nWriters) {
        super("SynchronizedHashMap ", nReaders, nWriters);
    }
}

class ConcurrentHashMapTest extends MapTest {

    @Override
    Map<Integer, Integer> containerInitializer() {
        return new ConcurrentHashMap<>(new HashMap<>(MapData.map(
                new CountingGenerator.Integer(),
                new CountingGenerator.Integer(),
                containerSize)));
    }

    public ConcurrentHashMapTest(int nReaders, int nWriters) {
        super("ConcurrentHashMap ", nReaders, nWriters);
    }
}
