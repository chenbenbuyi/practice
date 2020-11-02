package thinckingInJava.part17.com;

import thinckingInJava.part15_genericity.p3.Generator;

import java.util.ArrayList;

/**
 * @author chen
 * @date 2020/9/5 21:08
 * @Description
 */
public class CollectionData<T> extends ArrayList<T> {
    public CollectionData(Generator<T> gen, int quantity) {
        for (int i = 0; i < quantity; i++) {
            add(gen.next());
        }
    }

    public static <T> CollectionData<T> list(Generator<T> gen, int quantity) {
        return new CollectionData<>(gen, quantity);
    }
}
