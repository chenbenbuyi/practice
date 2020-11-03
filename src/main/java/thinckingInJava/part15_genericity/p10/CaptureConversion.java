package thinckingInJava.part15_genericity.p10;

/**
 * @author chen
 * @date 2020/10/29 0:21
 * @Description 捕获转换
 * 在方法内部，需要使用确切的类型，可以如下例使用，编译器在调用f2的使用，捕获了参数的具体类型，因而可以对f1进行具体参数类型的调用
 */
public class CaptureConversion {

    static <T> void f1(Holder<T> holder) {
        T t = holder.get();
        System.out.println(t.getClass().getSimpleName());
    }

    static void f2(Holder<?> holder) {
        f1(holder);
    }

    public static void main(String[] args) {
        Holder<Integer> raw = new Holder<>(1);
        f1(raw);
        f2(raw);
        Holder holder = new Holder();
        holder.set(new Object());
        f2(holder);
        Holder<?> doubleHolder = new Holder<>(1.0);
        f2(doubleHolder);

    }
}
