package java_concurrency.chapter3;

/**
 *  内部类、匿名内部类都可以访问外部类的对象的域，因为内部类构造的时候，编译器会把外部类的对象this隐式的作为一个参数传递给内部类的构造方法，
 */
public class ThisEscape {

    public static void main(String[] args) {

    }

    /**
     *  在该内部类的实例化过程中，由于构造ThisEscape的过程中，涉及了内部类，而内部类实际上是持有外部类的引用的
     *  这会导致，实际上我的 ThisEscape对象还没有构造完成，但我却已经在调用外部类的方法了
     *   这里一直没搞明白？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
     */
    public ThisEscape(EventSource source) {
        source.registerListener(new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        });
    }

    void doSomething(Event e) {
    }

    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }
}