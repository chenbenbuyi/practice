package thinckingInJava.part15_genericity.p11;

/**
 * @author chen
 * @date 2020/11/4 22:40
 */
public interface Inter<T> {
}

interface Inter2 {
}


class Child1 implements Inter<Child1> {}


//由于泛型擦除，一个类不能实现同一个泛型接口的两种变体
//class Child2 extends Child1 implements Inter<Child2>{}

/**
 *  但是，如果没有泛型参数声明，却可以实现，奇怪否
 */
class A implements Inter {}

class B extends A implements Inter2{}


