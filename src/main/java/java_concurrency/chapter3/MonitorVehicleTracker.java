package java_concurrency.chapter3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chen
 * @date 2021/1/16 15:00
 * @Description 利用监视器模式实现的一个线程安全的车辆追踪系统的线程安全类
 *  监视器模式就是简单的将可变状态封装起来，由内置锁进行保护。
 *  注意内置锁可以是任意的锁对象，不一定当前类的对象锁，只要始终使用的同一对象的锁都能起到线程同步访问的效果
 */
public class MonitorVehicleTracker {
    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint mutablePoint = locations.get(id);
        return mutablePoint == null ? null : new MutablePoint(mutablePoint);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint mutablePoint = locations.get(id);
        if (mutablePoint == null)
            throw new IllegalArgumentException("没有车辆id为：" + id);
        mutablePoint.x = x;
        mutablePoint.y = y;
    }


    /**
     *  虽然 MutablePoint 不是线程安全的类，但是封装其位置信息的MonitorVehicleTracker类是线程安全的
     *  这里要注意保障线程安全的两个点：
     *      ① 利用对象监视器模式，获取单个或所有位置的信息的方法和设置位置信息的方法都是同步的；
     *      ② 被封装的不安全对象或其容器类都私有化且不会被发布或逸出。
     *          针对第②点你可以发现两点重要信息值得学习：
     *              ① 代码中获取单个位置信息时返回的是新创建的位置对象（副本），而不是原始的位置对象；
     *              ② 返回的所有车辆的位置集合也不是本身封装的私有域 Map，而是将数据域进行深度拷贝，返回了一个不可变(unmodifiableMap)的Map。
     *
     */
    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
        Map<String, MutablePoint> result = new HashMap<>();
        for (String id : m.keySet()) {
            // 为什么要单独new ，参见主方法的测试结论
            result.put(id, new MutablePoint(m.get(id)));
        }
        return Collections.unmodifiableMap(result);
    }

    /**
     * Collections.unmodifiableMap 返回的指定map的不可变视图
     * 测试你会发现：
     *  虽然  Collections.unmodifiableMap  返回的视图集本身不支持增删改操作，但不影响你对集合内部数据的修改操作
     */
    public static void main(String[] args) {
        Map<String ,MutablePoint> map  = new HashMap<>();
        map.put("宝骏",new MutablePoint(1,2));
        map.put("长城", new MutablePoint(10,20));
        System.out.println(map);
        Map<String, MutablePoint> unmodifiableMap = Collections.unmodifiableMap(map);
//        unmodifiableMap.put("sdf",new MutablePoint(3,3) ); 直接异常
        MutablePoint mutablePoint = unmodifiableMap.get("宝骏");
        mutablePoint.x=12;
        mutablePoint.y=12;
        System.out.println(map);
    }
}

class MutablePoint {
    public int x, y;

    public MutablePoint() {
        this.x = 0;
        this.y = 0;
    }

    public MutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MutablePoint(MutablePoint point) {
        this.x = point.x;
        this.y = point.y;
    }

    @Override
    public String toString() {
        return "MutablePoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}