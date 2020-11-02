package thinckingInJava.part15_genericity.p10;

/**
 * @author chen
 * @date 2020/10/28 23:46
 * @Description 原生类型和无界通配符之间的差异
 */
public class Wildcards {

    static void rawArgs(Holder holder, Object arg) {
        /**
         *  将本属于泛型的类声明为原生类型，会让编译器放弃类型检查，但会因为类型安全性被编译器警告
         */
        holder.set(arg);
        holder.set(new Wildcards());
        Object o = holder.get();
    }

    static void unbounderdArg(Holder<?> holder, Object arg) {
        /**
         *  使用无界通配符之后，设值会产生编译错误 同{@link UnboundedWildCards2}中的assign2 assign3注释部分一样
         *  原生类型意味着对象可以持有任何类型，因此原生的Holder类型允许任意对象的插入
         *  Holder<?> 无界通配符意味着持有某种具体类型的同类型集合，虽然具体类型不确定，但总不能是一个任意的对象。这也导致无法插入
         */
//        holder.set(arg);
//        holder.set(new Wildcards());
        Object o = holder.get();
    }

    static <T> T exact1(Holder<T> holder) {
        T t = holder.get();
        return t;
    }

    static <T> T exact2(Holder<T> holder, T arg) {
        /**
         *  因为T是指代的一种明确的未知的泛型参数，而不是无界通配符表示的不确定的未知类型，所以可以设值
         */
        holder.set(arg);
        T t = holder.get();
        return t;
    }

    static <T> T wildSubtype(Holder<? extends T> holder, T arg) {
//        holder.set(arg);
        T t = holder.get();
        return t;
    }

    static <T> void wildSupertype(Holder<? super T> holder, T arg) {
        holder.set(arg);
//        T t = holder.get();
        Object object = holder.get();
    }

    public static void main(String[] args) {
        Holder<Long> raw = new Holder<>();
        raw = new Holder();
        Holder<Long> qualified = new Holder<>();
        Holder<?> unbounded = new Holder<>();
        Holder<? extends Long> bounded = new Holder<>();
        Long lng = 1L;
        rawArgs(raw, lng);
        rawArgs(qualified, lng);
        rawArgs(unbounded, lng);
        rawArgs(bounded, lng);

        unbounderdArg(raw, lng);
        unbounderdArg(qualified, lng);
        unbounderdArg(unbounded, lng);
        unbounderdArg(bounded, lng);

        Object obj = exact1(raw);
        Long aLong = exact1(qualified);
        Object o2 = exact1(unbounded);
        Long o3 = exact1(bounded);

        Long o5 = exact2(raw, lng);
        Long o6 = exact2(qualified, lng);
//        exact2(unbounded,lng );
//        exact2(bounded);

        Long o7 = wildSubtype(raw, lng);
        Long o8 = wildSubtype(qualified, lng);
//        wildSubtype(unbounded, lng);
        Long o9 = wildSubtype(bounded, lng);


        wildSupertype(raw, lng);
        wildSupertype(qualified, lng);
//        wildSupertype(unbounded, lng);
//        wildSupertype(bounded, lng);
    }


}
