package thinckingInJava.part15_genericity.p10;

/**
 * @author chen
 * @date 2020/10/28 23:44
 * @Description
 */
public class Holder<T> {
    private T value;

    public Holder() {
    }

    public Holder(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        return value.equals(o);
    }


}
