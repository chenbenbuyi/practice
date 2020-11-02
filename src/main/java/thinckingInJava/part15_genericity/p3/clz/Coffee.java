package thinckingInJava.part15_genericity.p3.clz;

/**
 * @author chen
 * @date 2020/10/11 21:44
 * @Description
 */
public class Coffee {
    private static long counter = 0;
    private final long id = counter++;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + id;
    }
}
