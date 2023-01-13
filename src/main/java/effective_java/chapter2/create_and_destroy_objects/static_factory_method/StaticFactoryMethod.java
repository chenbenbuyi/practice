package effective_java.chapter2.create_and_destroy_objects.static_factory_method;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;

/**
 * @author chen
 * @date 2021/9/11 9:42
 * @Description rule 1 考虑用静态工厂方法代替构造器
 * 有几点明显的好处：
 * ① 静态工厂方法的名字能够确切的描述被返回的对象
 * 示例：{@link BigInteger#probablePrime(int, java.util.Random)}
 * ② 通过预先构建的实例对象，静态工厂方法可以不用在每次调用时都单独创建对象，并为重复的调用返回相同的对象，有助于严格控制某个时刻哪些实例应该存在。
 * 这种类被称作实例受控的类（instance-controlled）。实例受控的类，可以确保它是一个单例或者不可实例化的，且能确保不可变的值类不会存在两个相等的实例，
 * 即当且仅当 a==b时，q.equals(b)才会true。这是享元模式的基础
 * <p>
 * <p>
 * 示例：{@link Boolean#valueOf(boolean)}
 * ③ 可以返回原返回类型的任何子类对象，为对象的创建提供足够的灵活性。比如以下几种应用：
 * a、API可以返回一个对象，但同时又不会使对象的类编程共有的，以此来隐藏实现类
 * 如 {@link Collections#synchronizedList(java.util.List)} 方法返回非共有的同步集合类，改类是List的子类，客户端应用可以操作但无法直接创建该实现类的对象
 * ④ 返回的对象可以随着每次调用参数的不同而不同，对外隐藏了内部判断逻辑。 而构造器中做过多繁杂的逻辑处理就显得不太优雅
 * 如 {@link EnumSet} 没有提供共有的构造器，通过静态工厂方法以底层枚举类型来决定返回两种子类型实例中的某一个，对客户端来说隐藏了具体实现
 * ⑤ 返回对象所属的类，在编写静态工厂方法时可以不存在。这种灵活性构成了服务提供者框架的基础，如JDBC
 * <p>
 * * 缺点 ：
 * 同构造器一样都不能很好的扩展大量的可选参数
 * <p>
 * 静态工厂方法命名规范：
 * from——类型转换：只包含单个参数，返回该类型的一个相对应的实例
 * {@link Date#from(java.time.Instant)}
 * of/allOf——聚合方法：带多个参数合并返回类的实例
 * {@link EnumSet#of(java.lang.Enum)}
 * instance/newInstance/getInstance
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
