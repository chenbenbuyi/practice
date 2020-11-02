package thinckingInJava.part17.com;

/**
 * @author chen
 * @date 2020/9/6 10:46
 * @Description Map 生成器接口
 */
public class Pair<K,V> {
    public final K key;
    public final V v;
    public Pair(K key,V v) {
        this.key = key;
        this.v = v;
    }
}
