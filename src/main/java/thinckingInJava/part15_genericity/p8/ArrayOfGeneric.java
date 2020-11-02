package thinckingInJava.part15_genericity.p8;

/**
 * @author chen
 * @date 2020/10/15 22:46
 * @Description
 */
public class ArrayOfGeneric {
    static final int SIZE = 100;
    static Generic<Integer>[] gia;

    public static void main(String[] args) {
        gia = (Generic<Integer>[])new Generic[SIZE];
        System.out.println(gia.getClass().getSimpleName()); // 输出 Generic[]，具体的泛型类型被擦除
        gia[0]= new Generic<>();
    }
}
