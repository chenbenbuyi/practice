package thinckingInJava.part17.p2;

import javafx.util.Pair;
import thinckingInJava.part15_genericity.p3.Generator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author chen
 * @date 2020/9/6 10:42
 * @Description 使用Map适配器进行Map的元素填充
 */
public class MapData<K, V> extends LinkedHashMap<K, V> {

    public MapData(Generator<Pair<K, V>> gen, int quantity) {
        for (int i = 0; i < quantity; i++) {
            Pair<K, V> pair = gen.next();
            put(pair.getKey(), pair.getValue());
        }
    }


    public MapData(Generator<K> genK, Generator<V> genV, int quantity) {
        for (int i = 0; i < quantity; i++) {
            put(genK.next(), genV.next());
        }
    }

    public MapData(Generator<K> genK, V value, int quantity) {
        for (int i = 0; i < quantity; i++) {
            put(genK.next(), value);
        }
    }

    public MapData(Iterable<K> genK, Generator<V> genV) {
        for (K k : genK) {
            put(k, genV.next());
        }
    }

    public MapData(Iterable<K> genK, V value) {
        for (K k : genK) {
            put(k, value);
        }
    }

    public static <K, V> MapData<K, V> map(Generator<Pair<K, V>> gen, int quantity) {
        return new MapData<>(gen, quantity);
    }

    public static <K, V> MapData<K, V> map(Generator<K> genK, Generator<V> genV, int quantity) {
        return new MapData<>(genK, genV, quantity);
    }

    public static <K, V> MapData<K, V> map(Generator<K> genK, V value, int quantity) {
        return new MapData<>(genK, value, quantity);
    }

    public static <K, V> MapData<K, V> map(Iterable<K> genK, Generator<V> genV) {
        return new MapData<>(genK, genV);
    }

    public static <K, V> MapData<K, V> map(Iterable<K> genK, V value) {
        return new MapData<>(genK, value);
    }

}


