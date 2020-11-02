package thinckingInJava.part19_enum.p1;

/**
 * @author chen
 * @date 2020/9/15 20:37
 * @Description
 */
public enum One2 {
    GROUND, CRAWLING, HANGING
    ;

    // 枚举的方法覆写和普通类没有区别 此处重写了toString方法，以方位自定义的name值。
    // 公共父类源码中 toString 和 name 方法是一个意思，区别是 name 声明为 final,不能覆写，toString则可以
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
