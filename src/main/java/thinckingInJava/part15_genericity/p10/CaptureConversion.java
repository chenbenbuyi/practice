package thinckingInJava.part15_genericity.p10;

/**
 * @author chen
 * @date 2020/10/29 0:21
 * @Description 捕获转换
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
        Holder<Integer> raw = new Holder<Integer>(1);
        f1(raw);
        f2(raw);
        Holder holder = new Holder();
        holder.set(new Object());
        f2(holder);
        Holder<?> doubleHolder = new Holder<>(1.0);
        f2(doubleHolder);

    }
}
