package thinckingInJava.part17_collection.p8_map;

import lombok.extern.slf4j.Slf4j;
import thinckingInJava.part17_collection.p2.Countries;

import java.util.*;

/**
 * @author chen
 * @date 2020/11/29 11:55
 * @Description
 * 该实例模拟类似于Map的结构，但是其获取值是基于遍历的线性查询方式，是最慢的一种查询方式
 */
@Slf4j
public class SlowMap<K, V> extends AbstractMap<K, V> {
    private List<K> keys = new ArrayList<>();
    private List<V> values = new ArrayList<>();


    @Override
    public V put(K key, V value) {
        V oldValue = get(key);
        if (!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        } else
            values.set(keys.indexOf(key), value);
        /**
         * 为了和Map 保持一致，必须返回旧值
         */
        return oldValue;
    }

    /**
     * @param key 注意，在API接口设计上，这里参数类型是Object,而不是参数化类型
     *            因为 java 泛型引入较晚
     */
    @Override
    public V get(Object key) {
        if (keys.contains(key)) {
            return values.get(keys.indexOf(key));
        }
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = new HashSet<>();
        Iterator<K> ki = keys.iterator();
        Iterator<V> vi = values.iterator();
        while (ki.hasNext()) {
            entries.add(new MapEntry<>(ki.next(), vi.next()));
        }
        return entries;
    }

    public static void main(String[] args) {
        SlowMap<String, String> map = new SlowMap<>();
        map.putAll(Countries.capitals(10));
        log.info("{}",map);
        log.info("{}",map.entrySet());
        log.info("{}",map.get("中国"));

    }
}
