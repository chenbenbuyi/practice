package java_concurrency.chapter2;

/**
 * @author chen
 * @date 2021/1/11 23:26
 * 针对可重入锁中的示例中的疑问测试。
 * 原文描述为：由于 Father 和 Child 中的 doSomething 方法都是同步方法，因此每个方法在执行前都会获得Father对象的锁
 * 如果锁内置锁不可重入，则super.doSomething将无法获得Father上的锁，从而导致死锁。
 * 疑问在于，方法调用中，执行线程到底获取的是父类还是子类对象的锁，又或者两个对象的锁？
 * 测试发现：创建子类对象调用子类方法时，父类同步方法获取的也是子类对象的锁
 */
public class Father {
    public synchronized void doSomething() {
        System.out.println("父类对象：" + this);
    }

    public static void main(String[] args) {
        Father father = new Child();
        father.doSomething();
    }

}

class Child extends Father {
    @Override
    public synchronized void doSomething() {
        System.out.println("子类对象："+this);
        System.out.println("父类对象："+super.hashCode());
        /**
         * 重要理解，super.doSomething(); 这句代码的到底是谁在调用？ 当然还是子类对象在调用
         * 所以，子类对于父类同步方法的访问其调用者是子类对象，所以线程获取也自然是子类对象的锁。
         */
        super.doSomething();
    }
}
