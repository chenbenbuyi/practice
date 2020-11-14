package thinckingInJava.part16.p7_arrays.compar;

import java.util.Comparator;

/**
 * @author chen
 * @date 2020/11/13 23:49
 * @Description 如果定义的类本身不支持排序(即没有实现Comparable接口)，可以单独自定义比较器 Comparator，将类的不同实例的比较规则在比较器中进行定义
 */
public class MyComparator implements Comparator<ComparableUser> {

    @Override
    public int compare(ComparableUser o1, ComparableUser o2) {
        return o2.getAge() - o1.getAge();
    }
}
