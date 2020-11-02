package thinckingInJava.part15_genericity.p3;

/**
 *  该 泛型接口 是工厂方法设计模式的一种应用
 * @param <T>
 */
public interface Generator<T> {
    /**
     *  只定义一个用于产生泛型化的参数对象的方法
     * @return 参数化的 T
     */
    T next();
}