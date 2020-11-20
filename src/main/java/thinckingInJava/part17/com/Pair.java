package thinckingInJava.part17.com;

/**
 * @author chen
 * @date 2020/9/6 10:46
 * @Description 用于Map的键值对结构
 */
public class Pair<K,V> {
    /**
     *  final  定义是为了让对象实例为只读对象，一旦构建就不可更改
     */
    public final K key;
    public final V v;
    public Pair(K key,V v) {
        this.key = key;
        this.v = v;
    }
}
