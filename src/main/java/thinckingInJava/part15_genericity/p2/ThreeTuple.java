package thinckingInJava.part15_genericity.p2;

/**
 * @author chen
 * @date 2020/10/9 21:42
 * @Description 通过继承实现长度更长的元组
 */
public class ThreeTuple<A, B, C> extends TwoTuple<A, B> {
    public final C third;

    public ThreeTuple(A first, B second, C third) {
        super(first, second);
        this.third = third;
    }

    @Override
    public String toString() {
        return "ThreeTuple{" +
                "third=" + third +
                ", first=" + first +
                ", second=" + second +
                '}';
    }
}

