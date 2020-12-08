package thinckingInJava.part17_collection.p10_test;

import java.util.*;

/**
 * @author chen
 * @date 2020/12/8 23:56
 * @Description Set 性能测试
 */
public class SetPerformance {
    static List<Test<Set<Integer>>> tests = new ArrayList<>();

    static {
        tests.add(new Test<Set<Integer>>("add") {
            @Override
            int test(Set<Integer> set, TestParam tp) {
                int loops = tp.loops;
                int size = tp.size;
                for (int i = 0; i < loops; i++) {
                    set.clear();
                    for (int i1 = 0; i1 < size; i1++) {
                        set.add(i1);
                    }
                }
                return loops * size;
            }
        });

        tests.add(new Test<Set<Integer>>("contains") {
            @Override
            int test(Set<Integer> set, TestParam tp) {
                int loops = tp.loops;
                int span = tp.size * 2;
                for (int i = 0; i < loops; i++) {
                    for (int i1 = 0; i1 < span; i1++) {
                        set.contains(i1);
                    }
                }
                return loops * span;
            }
        });
        tests.add(new Test<Set<Integer>>("iterate") {
            @Override
            int test(Set<Integer> set, TestParam tp) {
                int loops = tp.loops * 10;
                for (int i = 0; i < loops; i++) {
                    Iterator<Integer> iterator = set.iterator();
                    while (iterator.hasNext()) {
                        iterator.next();
                    }
                }
                return loops * set.size();
            }
        });
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            Tester.defaultParams = TestParam.array(args);
        }
        Tester.fieldWildth = 10;
        Tester.run(new TreeSet<>(), tests);
        Tester.run(new HashSet<>(), tests);
        Tester.run(new LinkedHashSet<>(), tests);
    }
}
