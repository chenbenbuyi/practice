package java_concurrency.chapter4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chen
 * @date 2021/1/16 17:58
 * @Description 基于委托的线程安全类示例
 * 所谓基于委托，即是将非线程安全类的安全性保证委托给线程安全的类
 */
public class DelegatingVehicleTracker {
    private final ConcurrentHashMap<String, ImmutablePoint> locations;
    private final Map<String, ImmutablePoint> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, ImmutablePoint> points) {
        this.locations = new ConcurrentHashMap<>(points);
        this.unmodifiableMap = Collections.unmodifiableMap(points);
    }

    /**
     * 返回的是原数据的视图。和快照的区别在于 视图会实时反映原数据的变化
     */
    public Map<String, ImmutablePoint> getLocations() {
        return unmodifiableMap;
    }

    /**
     *  返回的是一个不会发生变化的浅拷贝（静态拷贝）数据
     *  由于locations的键值都是不可变数据，因此只用拷贝Map结构而不用复制其内容。这样，如果 locations中的数据发生变化，getShallowCopyLocations 返回的浅拷贝对象对应的数据也不会发生变化
     *      这里重要的点在于理解什么是不可变对象。这里正是因为对象不可变，一旦创建对象，就无法修改，只能用新对象替换。对于Map来说，原始Map设置键新的值，和现在的静态拷贝对象已经没有啥关系了
     *  HashMap(Map<? extends K,? extends V> m) 构造一个新的HashMap与指定的相同的映射Map
     */
    public Map<String, ImmutablePoint> getShallowCopyLocations() {
        return Collections.unmodifiableMap(new HashMap<>(locations));
    }

    /**
     * HashMap(Map<? extends K,? extends V> m) 构造一个新的HashMap与指定的相同的映射Map
     *  由此可以猜想：
     *      ① 通过 new HashMap<>(map) 构建的map 中的数据和原始Map相比，其 k 和 v 指向的对象应该是相同的
     *      ② 通过 改变 new HashMap<>(map) 产生的 Map 中相应的值应该也会改变原始 Map中的值
     *      ③ 对于原始的和浅拷贝的Map, 相互映射的值的修改会相互影响，其它的增删该没有关联，和操作普通Map无区别
     */
    public static void main(String[] args) {
        Map<String ,MutablePoint> map  = new HashMap<>();
        map.put("宝骏",new MutablePoint(1,2));
        map.put("长城", new MutablePoint(10,20));
        for (Map.Entry<String, MutablePoint> entry : map.entrySet()) {
            System.out.println("原始map中的Key值哈希："+entry.getKey().hashCode()+",value值哈希："+entry.getValue().hashCode());
        }
        HashMap<String, MutablePoint> shallowCopyMap = new HashMap<>(map);
        /**
         *  由于 new HashMap<>(map) 是浅拷贝，因此数据集中的数据理论上都是指向的原始值
         */
        for (Map.Entry<String, MutablePoint> entry : shallowCopyMap.entrySet()) {
            System.out.println("shallowCopyMap中的Key值哈希："+entry.getKey().hashCode()+",value值哈希："+entry.getValue().hashCode());
        }
        System.out.println("原始map:"+shallowCopyMap);
//        shallowCopyMap.put("宝骏", new MutablePoint(3000,2));  直接这样肯定不会改变原map，因为这只不过是把本来映射相同对象的引用指向了一个新对象，和原始的Map更加没有关联了
        MutablePoint mutablePoint = map.get("宝骏");
        mutablePoint.x=1000;
        mutablePoint.y=1000;
        System.out.println("原始map2:"+shallowCopyMap);
        map.put("新增", new MutablePoint(3,3));  // 新增也不会影响原来的值
        // 删除操作，会影响原始map 吗？咋一想好似会，但再想想删除到底是删除的什么？删除表示该map中不在存对应键的值了，那和原始map有啥关系呢？
        map.remove("宝骏");
        System.out.println("原始map3:"+shallowCopyMap);
        for (Map.Entry<String, MutablePoint> entry : map.entrySet()) {
            System.out.println("原始map中的Key值哈希："+entry.getKey().hashCode()+",value值哈希："+entry.getValue().hashCode());
        }
    }


    public ImmutablePoint getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (locations.replace(id, new ImmutablePoint(x, y)) == null) {
            throw new IllegalArgumentException("未找到相应车辆id：" + id);
        }
    }
}


class ImmutablePoint {
    private final int x, y;

    public ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "ImmutablePoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}