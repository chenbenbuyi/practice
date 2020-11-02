package thinckingInJava.part15_genericity.p2;

/**
 * @author chen
 * @date 2020/10/9 21:40
 * @Description 元组对象
 *
 * 元组在计算机领域有着特殊的意义，这个名字听起来似乎有些陌生， 平时在写代码也基本没什么应用场景， 然而， 出人意料的是， 元组跟程序设计密切相关， 可能有的同学不知道， 关系数据库中的「纪录」的另一个学术性的名称就是「元组」，
 * 一条记录就是一个元组， 一个表就是一个关系， 纪录组成表， 元组生成关系， 这就是关系数据库的核心理念。元组并不像数组、对象那样是不可缺少的编程元素，但是， 使用它却能对编写代码带来很多的便利，尤其是当一个函数需要返回多个值的情况下。
 * 元组是关系数据库不可脱离的部份， 但是在程序设计中， 元组并不显得那么不可或缺。 有一些编程语言本身就自带元组的语法， 比如说python、F#、haskell、scala等，
 * 另一些更为流行的编程语言却不带元组语法， 如Java、JavaScript、c++、c#等，要使用元组必须自行实现，所幸现在这些编程语言都支持泛型， 实现非内置元组也变的异常简单， 但是毕竟是非语言内置的语法元素，使用起来肯定不如原生元组来的便捷
 */
public class TwoTuple<A,B> {
    public final A first;
    public final B second;

    public TwoTuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "TwoTuple{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
