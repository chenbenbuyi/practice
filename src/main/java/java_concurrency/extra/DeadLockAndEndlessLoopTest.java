package java_concurrency.extra;

/**
 * @date: 2021/1/14 12:29
 * @author: chen
 * @desc: 死锁和死循环
 * 项目运行，死锁和死循环有啥区别，如何判断代码是死锁还是死循环？有哪些现象和工具可以诊断？
 */
public class DeadLockAndEndlessLoopTest {

    /**
     *  死锁和死循环
     *   首先，程序运行对系统资源的占用是不一样的。死锁程序会挂起，无法继续执行，但死循环程序却是一直在占用CPU资源一直运行。
     *      所以很明显的死锁不会占用CPU资源，但死循环会导致CPU飙升。
     *      （ps：是否消耗CPU其实也分锁的类别，对于对象内置锁来说是没有CPU消耗的，但是对于像自旋等待等这种锁机制，还是会导致CPU飙升。换句话说，不能仅凭CPU是否飙升来判断死循环还是死锁，需要进一步诊断）
     *
     */
    public static void main(String[] args) {
        if (Math.random() > 0.5) {
            endlessLoop();
        } else {
            deadLock();
        }

    }


    /**
     *  两个线程相互持有对方想要的锁
     *  注意：由于本示例代码临界区没有添加延迟处理，所以代码死锁不一定总是发生，依赖于线程调度的先后。
     *  如果线程 1 或 2 在另一个线程获取锁之前优先获取到了两个对象的锁，那么死锁情况就不会发生
     */
    public static void deadLock() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        new Thread(() -> {
            synchronized (obj1) {
                System.out.println(Thread.currentThread().getName()+"获取obj1的监视器锁进入临界区想要获取obj2的监视器锁");
                synchronized (obj2){
                    System.out.println(Thread.currentThread().getName()+"获取到obj2的监视器锁");
                }
            }
        },"线程1").start();

        new Thread(() -> {
            synchronized (obj2) {
                System.out.println(Thread.currentThread().getName()+"获取obj2的监视器锁进入临界区想要获取obj1的监视器锁");
                synchronized (obj1){
                    System.out.println(Thread.currentThread().getName()+"获取到obj1的监视器锁");
                }
            }
        },"线程2").start();

    }

    public static void endlessLoop() {
        while (true) {
            System.out.println("死循环");
        }
    }
}
