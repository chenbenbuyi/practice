package effective.chapter2.create_and_destroy_objects.singleton;

/**
 * @author chen
 * @date 2023/2/8 22:50
 * @Description Singleton with public final field
 */
public class Singleton {

    /**
     * 通过共用的静态 final 域 提供该单例类的唯一的实例
     *
     * <p>
     * 不过该种方式无法抵御反射攻击
     */
//    public static final Singleton INSTANCE = new Singleton();

    /**
     *  静态域私有，通过静态工厂方法返回单例对象
     */
    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {

    }

    public static Singleton getInstance() {
        return INSTANCE;
    }
}
