package thinckingInJava.part15_genericity.p8;

/**
 * @author chen
 * @date 2020/10/14 23:59
 * @Description
 */
public interface IFactory<T> {
    T create();
}
