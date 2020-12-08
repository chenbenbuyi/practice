package thinckingInJava.part17_collection.p10_test;

/**
 * @author chen
 * @date 2020/12/1 23:15
 * @Description 性能测试基础类 泛型 C 表示要测试的容器
 */
public abstract class Test<C> {
    String name;

    /**
     *  构造器便于构造每个测试对象的名字
     */
    public Test(String name) {
        this.name = name;
    }

    abstract int test(C list, TestParam tp);
}
