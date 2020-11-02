package thinckingInJava.part19_enum.p8;

import java.util.EnumSet;

/**
 * @author chen
 * @date 2020/9/18 22:12
 * @Description
 * EnumSet是一个抽象类，它没有定义使用的向量长度，它有两个子类，RegularEnumSet和JumboEnumSet。
 * RegularEnumSet使用一个long类型的变量作为位向量，long类型的位长度是64，而JumboEnumSet使用一个long类型的数组。
 * 如果枚举值个数小于等于64，则静态工厂方法中创建的就是RegularEnumSet，大于64的话就是JumboEnumSet。
 *
 * EnumSet 中元素的顺序和其关联enum元素定义的顺序一致
 */
public class EnumSetTest {
    public static void main(String[] args) {
        EnumSet<Week> weeks = EnumSet.allOf(Week.class);
        weeks.removeAll(EnumSet.of(Week.SUNDAY));
        System.out.println(weeks);
    }
}
