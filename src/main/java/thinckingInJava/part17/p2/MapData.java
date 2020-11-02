package thinckingInJava.part17.p2;

import thinckingInJava.part15_genericity.p3.Generator;

import java.util.LinkedHashMap;

/**
 * @author chen
 * @date 2020/9/6 10:42
 * @Description
 */
public class MapData<K, V> extends LinkedHashMap<K, V> {

    public MapData(Generator<K> genK, Generator<V> genV, int quantity) {
        for (int i = 0; i < quantity; i++) {
            put(genK.next(), genV.next());
        }
    }

    public static <K, V> MapData<K, V> map(Generator<K> genK, Generator<V> genV, int quantity) {
        return new MapData<>(genK, genV, quantity);
    }
}
