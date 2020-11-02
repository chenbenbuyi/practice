package thinckingInJava.part15_genericity.p4;

import thinckingInJava.part15_genericity.p3.Generator;

/**
 * @author chen
 * @date 2020/10/11 23:45
 * @Description
 */
public class BasicGenerator<T> implements Generator<T> {
    private Class<T> type;

    public BasicGenerator(Class<T> type) {
        this.type = type;
    }

    @Override
    public T next() {
        try {
            return type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Generator<T> create(Class<T> type) {
        return new BasicGenerator<>(type);
    }
}
