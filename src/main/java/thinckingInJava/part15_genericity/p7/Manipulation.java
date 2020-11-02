package thinckingInJava.part15_genericity.p7;

/**
 * @author chen
 * @date 2020/10/12 23:48
 * @Description
 */
public class Manipulation<T> {
    T obj;

    public Manipulation(T obj) {
        this.obj = obj;
    }
    public void test(){
        /**
         *  由于没有声明泛型参数的边界，也就无法确信，真正使用的T就是有 f()方法的对象类型
         */
//        obj.f();
    }
}

class Manipulation2<T extends HasF> {
    T obj;

    public Manipulation2(T obj) {
        this.obj = obj;
    }
    public void test(){
        /**
         *   和Manipulation进行比较体会
         */
        obj.f();
    }

}
