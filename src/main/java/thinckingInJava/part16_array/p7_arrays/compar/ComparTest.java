package thinckingInJava.part16_array.p7_arrays.compar;

import java.util.Arrays;

/**
 * @author chen
 * @date 2020/11/13 23:30
 * @Description
 */
public class ComparTest {
    public static void main(String[] args) {
        ComparableUser[] comparableUsers = {new ComparableUser("大陈", 50),new ComparableUser("中陈", 35),new ComparableUser("小陈", 18),new ComparableUser("小小陈", 10)};
        System.out.println(Arrays.toString(comparableUsers));
        // 利用对象本身的比较规则进行比较
        Arrays.sort(comparableUsers);
        System.out.println(Arrays.toString(comparableUsers));
        // 利用自定义的比较器定义的规则进行比较
        Arrays.sort(comparableUsers,new MyComparator());
        System.out.println(Arrays.toString(comparableUsers));
    }
}
