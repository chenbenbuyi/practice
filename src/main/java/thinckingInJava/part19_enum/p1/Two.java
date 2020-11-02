package thinckingInJava.part19_enum.p1;

/**
 * @author chen
 * @date 2020/9/15 20:37
 * @Description
 */
public enum Two {
    CHEN("陈姓",1), LI("栗姓",2),GAO("高姓",3)
    ;

    private String name;
    private Integer order;

    // 枚举的构造器不用显示的声明为私有的，因为只能在枚举内部使用其构造器创建枚举实例
    Two(String name, Integer order) {
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public Integer getOrder() {
        return order;
    }
}
