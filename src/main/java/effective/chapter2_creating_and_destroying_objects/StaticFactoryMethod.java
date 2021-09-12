package effective.chapter2_creating_and_destroying_objects;

import java.math.BigInteger;
import java.util.Date;
import java.util.EnumSet;

/**
 * @author chen
 * @date 2021/9/11 9:42
 * @Description 考虑用静态工厂方法代替构造器
 * 有几点明显的好处：
 * ① 静态工厂方法的名字能够哦确切的描述被返回的对象
 * 示例：{@link BigInteger#probablePrime(int, java.util.Random)}
 * ② 通过预先构建的实例对象，静态工厂方法可以不用在每次调用时都单独创建对象
 * 示例：{@link Boolean#valueOf(boolean)}
 * ③ 可以返回类型的任何子类对象，为对象的创建提供足够的灵活性
 * ④ 返回的对象可以随着每次调用参数的不同而不同，对外隐藏了内部判断逻辑。 而构造器似乎不适合做过多繁杂的逻辑处理？
 * ⑤ 返回对象所属的类，在编写静态工厂方法时可以不存在
 *
 * * 缺点 ：
 *  同构造器一样都不能很好的扩展大量的可选参数
 *
 * 静态工厂方法命名规范：
 *  from——类型转换：只包含单个参数，返回该类型的一个相对应的实例
 *          {@link Date#from(java.time.Instant)}
 *  of/allOf——聚合方法：带多个参数合并返回类的实例
 *          {@link EnumSet#of(java.lang.Enum)}
 *  instance/newInstance/getInstance
 */
public class StaticFactoryMethod {

    public static final StaticFactoryMethod STATIC_FACTORY_METHOD = new StaticFactoryMethod("默认对象");

    private String field;

    public StaticFactoryMethod(String field) {
        this.field = field;
    }

    /**
     * 通过预先构建的实例对象，静态工厂方法可以不用在每次调用时都单独创建对象
     */
    public static StaticFactoryMethod defaultObject() {
        return STATIC_FACTORY_METHOD;
    }

    /**
     * 返回对象所属的类，在编写静态工厂方法时可以不存在
     */
    public static MyInterface noImplClass() {
        return new MyInterface() {
        };
    }


}

interface MyInterface {

}
