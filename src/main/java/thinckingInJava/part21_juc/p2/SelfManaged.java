package thinckingInJava.part21_juc.p2;

/**
 * @author chen
 * @date 2020/4/6 10:09
 * @Description
 */
public class SelfManaged implements Runnable {

    private int countDown=5;

    private Thread t = new Thread(this);

    public SelfManaged() {
        // 线程的启动时在构造函数中进行的，在多线程的情况下，可能会有线程安全问题，因为另一个任务可能会在构造器结束之前开始执行
        // 也就意味着，该任务能够访问处于不稳定状态的对象
        t.start();
    }


    @Override
    public String toString() {
        return Thread.currentThread().getName()+"("+countDown+")";
    }

    @Override
    public void run() {
        while (true){
            System.out.println(this);
            if(countDown--==0)
                return;
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i <5; i++) {
            new SelfManaged();
        }
    }
}
