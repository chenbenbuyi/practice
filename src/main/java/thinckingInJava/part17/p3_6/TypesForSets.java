package thinckingInJava.part17.p3_6;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author chen
 * @date 2020/11/25 22:49
 * @Description
 */
class SetType {
    int i;

    public SetType(int i) {
        this.i = i;
    }

    /**
     * set 不存储重复元素，所以用来被放入任何形式的Set中的元素，都应该定义其 equals 方法来确保其唯一性
     * hashCode方法则为了基于哈希散列的快速查找，在有哈希的Set实现，如HashSet 和LinkedHashSet 中，放入的元素必须定义其 hashCode方法
     * 类似TreeSet 则不是必须，但是基于编程规范，重写equals 方法的同时总是应该同时重写 hashCode方法
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof SetType && (((SetType) obj).i == i);
    }

    @Override
    public String toString() {
        return Integer.toString(i);
    }
}

class HashType extends SetType {

    public HashType(int i) {
        super(i);
    }

    @Override
    public int hashCode() {
        return i;
    }
}

/**
 * TreeSet 有序，底层为树结构，元素必须实现Comparable 接口以实现其有序性
 */
class TreeType extends SetType implements Comparable<TreeType> {

    public TreeType(int i) {
        super(i);
    }

    @Override
    public int compareTo(TreeType o) {
        /**
         *  此处切忌优化为形如 return o.i -i 的形式，因为 Java 是有符号数，
         *  如果 i -j 在i很大的正数，j也很大的负数时候，两者相减产生溢出，结果为负值
         */
        return Integer.compare(o.i, i);
    }
}

public class TypesForSets {
    static <T> Set<T> fill(Set<T> set, Class<T> clz) {
        try {
            /**
             *  int.class 和 Integer.TYPE 是等价的(其它基本数据类型和void都有类似定义)，但和 Integer.class 并不等价
             */
            for (int i = 0; i < 10; i++) {
                set.add(clz.getConstructor(int.class).newInstance(i));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return set;
    }

    static <T> void test(Set<T> set, Class<T> clz) {
        fill(set, clz);
        System.out.println(set);
        fill(set, clz);
        System.out.println(set);
        fill(set, clz);
        System.out.println(set);
    }

    public static void main(String[] args) {
        test(new HashSet<>(), HashType.class);
        test(new LinkedHashSet<>(), HashType.class);
        test(new TreeSet<>(), TreeType.class);

        test(new HashSet<>(), SetType.class); // 没有自定义hashCode，会产生重复数据
        test(new TreeSet<>(), TreeType.class);
        // SetType和TreeType都没有自定义定义hashCode方法，都会产生重复数据
        test(new LinkedHashSet<>(), SetType.class);
        test(new LinkedHashSet<>(), TreeType.class);

        /**
         *  因为没有实现Comparable 接口定义比较逻辑，会抛出异常
         */
        test(new TreeSet<>(), SetType.class);
        test(new TreeSet<>(), HashType.class);
    }
}

