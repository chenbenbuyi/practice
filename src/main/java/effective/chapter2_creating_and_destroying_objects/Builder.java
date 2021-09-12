package effective.chapter2_creating_and_destroying_objects;

/**
 * @author chen
 * @date 2021/9/12 8:29
 * @Description 构造参数过多时，考虑使用构建器
 *      多构造参数时，可以通过重叠构造器或者JavaBeans模式(构造对象后通过setter方法设值)进行对象的构建；
 *      但是前者代码不易编写和阅读，后者在对象构造过程中会有线程安全问题，因为对象的状态在构建过程中处于不一致的状态，而且也无法将类做成不可变类
 */
public class Builder {
}
