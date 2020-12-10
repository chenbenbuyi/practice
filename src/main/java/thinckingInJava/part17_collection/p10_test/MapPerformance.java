package thinckingInJava.part17_collection.p10_test;

import java.util.*;

/**
 * @author chen
 * @date 2020/12/10 23:58
 * @Description 对Map 不同实现的性能测试比较
 */
public class MapPerformance {
    static List<Test<Map<Integer, Integer>>> tests = new ArrayList<>();

    static {
        tests.add(new Test<Map<Integer, Integer>>("put") {
            @Override
            int test(Map<Integer, Integer> map, TestParam tp) {
                int loops = tp.loops;
                int size = tp.size;
                for (int i = 0; i < loops; i++) {
                    map.clear();
                    for (int i1 = 0; i1 < size; i1++) {
                        map.put(i1, i1);
                    }
                }
                return loops * size;
            }
        });

        tests.add(new Test<Map<Integer, Integer>>("get") {
            @Override
            int test(Map<Integer, Integer> map, TestParam tp) {
                int loops = tp.loops;
                int span = tp.size * 2;
                for (int i = 0; i < loops; i++) {
                    for (int i1 = 0; i1 < span; i1++) {
                        map.get(i1);
                    }
                }
                return loops * span;
            }
        });

        tests.add(new Test<Map<Integer, Integer>>("interate") {
            @Override
            int test(Map<Integer, Integer> map, TestParam tp) {
                int loops = tp.loops * 10;
                for (int i = 0; i < loops; i++) {
                    Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
                    while (iterator.hasNext()) {
                        iterator.next();
                    }
                }
                return loops * map.size();
            }
        });
    }


    public static void main(String[] args) {
        Tester.run(new TreeMap<>(), tests);
        Tester.run(new HashMap<>(), tests);
        Tester.run(new LinkedHashMap<>(), tests);
        Tester.run(new IdentityHashMap<>(), tests);
        Tester.run(new WeakHashMap<>(), tests);
        Tester.run(new Hashtable<>(), tests);
    }
}
