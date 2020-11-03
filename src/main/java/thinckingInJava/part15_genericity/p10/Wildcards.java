package thinckingInJava.part15_genericity.p10;

/**
 * @author chen
 * @date 2020/10/28 23:46
 * @Description 原生类型和无界通配符之间的差异
 *  泛型中使用确切类型参数，因为明确性，所以可以让泛型参数做更多的事情——因为类型确认了，对象有哪些API就是确定的，只不过参数的类型是受限制的
 *  使用通配符，可以扩展泛型参数的类型，但因为类型未知性，必须有开发者在使用中来保障类型的正确性，灵活性是有，但失掉了编译器的类型检查，有强制转换错误的风险
 *
 */
public class Wildcards {

    static void rawArgs(Holder holder, Object arg) {
        /**
         *  将本属于泛型的类声明为原生类型，会让编译器放弃类型检查，但会因为类型安全性被编译器警告。类型正确性只能有开发者自己来保障
         */
        holder.set(arg);
        holder.set(new Wildcards());
        Object o = holder.get();
    }

    static void unbounderdArg(Holder<?> holder, Object arg) {
        /**
         *  使用无界通配符之后，设值会产生编译错误 同{@link UnboundedWildCards2}中的assign2 assign3注释部分一样
         *  原生类型意味着对象可以持有任何类型，因此原生的Holder类型允许任意对象的插入
         *  Holder<?> 无界通配符意味着持有某种具体类型的同构集合，虽然具体类型不确定，但总不能是一个任意的对象。这也导致其无法插入任何元素类型
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

    /**
     * Holder<? extends T>  的声明和声明为具体的类型如Holder<? extends Fruit> 没有区别。
     * 表示Holder类型的上限被放松为包括扩展自 T的任一类型对象，但又不同于原生类型的任何类型，由于任一不可知，编译器不允许进行插入操作
     * 但是你可以从该集合获取，因为无论如何，获取的类型肯定是T类型，这是相当明确的
     */
    static <T> T wildSubtype(Holder<? extends T> holder, T arg) {
//        holder.set(arg);
        T t = holder.get();
        return t;
    }

    /**
     *  超类通配符，标识任何T类型的基类元素，所以，只要是符合方法参数声明的任何T类型或其父类型元素元素都可以进行插入，
     *  但是无法获取，因为编译器无法知道元素对象具体为 super T的众多父类中哪一类元素，Object除外。只能自己进行实际对象的强转
     */
    static <T> void wildSupertype(Holder<? super T> holder, T arg) {
        holder.set(arg);
//        T t = holder.get();
        Object object = holder.get();
    }

    public static void main(String[] args) {
        Holder<Long> raw =  new Holder();
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
        Object o = wildSubtype(unbounded, lng);
        Long o9 = wildSubtype(bounded, lng);


        wildSupertype(raw, lng);
        wildSupertype(qualified, lng);
//        wildSupertype(unbounded, lng);
//        wildSupertype(bounded, lng);
    }


}
