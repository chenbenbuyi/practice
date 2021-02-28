package java_concurrency.chapter10_liveness;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/2/28 21:46
 * @Description 演示一种协作对象之间的死锁，通常都是因为在一个持有锁的方法中调用了某个外部方法，而这个外部方法又是加锁方法
 *   在持有锁的情况下调用外部方法，一定要警惕死锁。尽量使用开放调用，即不要在方法上直接加锁，而是满足同步需求的情况下，采用同步代码块构建临界区，尽量缩小锁的粒度
 *
 */
public class CollaborationDeadlock {

    private Other other;

    public CollaborationDeadlock(Other other) {
        this.other = other;
    }

    public synchronized void test1() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);// 模拟任务执行耗时
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        other.fun1();
    }

    public synchronized void test2() {
        System.out.println("doSomething");
    }

    public static void main(String[] args) throws InterruptedException {
        Other other = new Other();
        CollaborationDeadlock collaborationDeadlock = new CollaborationDeadlock(other);
        other.setCollaborationDeadlock(collaborationDeadlock);
        new Thread(() -> collaborationDeadlock.test1()).start();
        new Thread(() -> other.fun2()).start();
    }

}

class Other {

    private CollaborationDeadlock collaborationDeadlock;

    public Other() {
    }

    public void setCollaborationDeadlock(CollaborationDeadlock collaborationDeadlock) {
        this.collaborationDeadlock = collaborationDeadlock;
    }

    public synchronized void fun1() {
        System.out.println("外部类的同步方法");
    }

    public synchronized void fun2() {
        collaborationDeadlock.test2();
        System.out.println("外部类的另一个同步方法");
    }
}
