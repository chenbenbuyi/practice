package thinckingInJava.part15_genericity.p8;

/**
 * @author chen
 * @date 2020/10/15 0:02
 * @Description 通过工厂创建对象
 */
public class Foo<T> {
    private T t;

    public <F extends IFactory<T>> Foo(F f) {
        t = f.create();
    }

    public static void main(String[] args) {
        IntegerFactory integerFactory = new IntegerFactory();
        Foo<Integer> integerFoo = new Foo<>(integerFactory);
        Foo<Widget> widgetFoo = new Foo<>(new Widget.Factory());
    }
}

class IntegerFactory implements IFactory<Integer> {

    @Override
    public Integer create() {
        return new Integer(100);
    }
}

class Widget {
    // 嵌套类的实现
    public static class Factory implements IFactory<Widget> {
        @Override
        public Widget create() {
            return new Widget();
        }
    }
}