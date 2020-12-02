package thinckingInJava.part17_collection.p10_test;

/**
 * @author chen
 * @date 2020/12/1 23:15
 * @Description 性能测试基础类
 */
public abstract class Test<C> {
    String name;

    public Test(String name) {
        this.name = name;
    }

    abstract int test(C list, TestParam tp);
}
