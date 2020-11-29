package thinckingInJava.part17_collection.p8_map;

import lombok.extern.slf4j.Slf4j;
import thinckingInJava.part17_collection.p2.Countries;

import java.util.*;

/**
 * @author chen
 * @date 2020/11/29 16:52
 * @Description
 */

@Slf4j
public class SimpleHashMap<K, V> extends AbstractMap<K, V> {
    static final int SIZE = 997;
    LinkedList<MapEntry<K, V>>[] buckets = new LinkedList[SIZE];

    @Override
    public V put(K key, V value) {
        V oldValue = null;
        /**
         *  此处有个sonar警告——Neither "Math.abs" nor negation should be used on numbers that could be "MIN_VALUE"
         *  这是因为存在最值溢出的可能，比如 Math.abs(Integer.MIN_VALUE) 因为最大值溢出，值仍然为其负数本身 Integer.MIN_VALUE
         */
        int index = Math.abs(key.hashCode()) % SIZE;
        // 如果为空，说明该哈希值取模后对应的槽位没有放过值
        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }
        LinkedList<MapEntry<K, V>> bucket = buckets[index];
        MapEntry<K, V> pair = new MapEntry<>(key, value);
        boolean found = false;
        ListIterator<MapEntry<K, V>> it = bucket.listIterator();
        while (it.hasNext()) {
            MapEntry<K, V> entry = it.next();
            if (Objects.equals(entry.getKey(), key)) {
                oldValue = entry.getValue();
                it.set(pair);
                found = true;
                break;
            }
        }
        // 没有发现相同元素，将新值添加到链表
        if (!found) {
            buckets[index].add(pair);
        }
        return oldValue;
    }

    @Override
    public V get(Object key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null) return null;
        for (MapEntry<K, V> entry : buckets[index]) {
            if (Objects.equals(entry.getKey(), key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (LinkedList<MapEntry<K, V>> bucket : buckets) {
            if (bucket == null) continue;
            for (MapEntry<K, V> entry : bucket) {
                set.add(entry);
            }
        }
        return set;
    }

    public static void main(String[] args) {
        SimpleHashMap<String, String> smp = new SimpleHashMap<>();
        smp.putAll(Countries.capitals(10));
        log.info("{}", smp);
        log.info("{}", smp.get("米国"));
        log.info("{}", smp.entrySet());

        /**
         *  String 对象有个特点：如果String对象又相同的字符序列，那么其哈希值相同，因为他们都映射到了同一内存区域
         *  所以，其哈希值是 基于内容的
         */
        int hashCode = new String("hashCode").hashCode();
        int hashCode2 = new String("hashCode").hashCode();
        int hashCode3 = "hashCode".hashCode();
        log.info("两个对象的哈希值：{}，{}",hashCode,hashCode2);
        log.info("两个对象的哈希值：{}，{}",hashCode2,hashCode3);
    }
}
