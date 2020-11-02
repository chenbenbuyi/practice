package thinckingInJava.part19_enum.p11;

/**
 * @author chen
 * @date 2020/9/24 7:42
 * @Description 定义的类型接口规定比较的T必须是Competitor的实例
 */
public interface Competitor<T extends Competitor<T>> {
    Outcome compete(T t);
}
