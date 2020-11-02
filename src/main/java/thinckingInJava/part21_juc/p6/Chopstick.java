package thinckingInJava.part21_juc.p6;

/**
 * @author chen
 * @date 2020/7/25 20:55
 * @Description
 */
public class Chopstick {
    private boolean token = false;
    public synchronized void take() throws InterruptedException {
        while (token){
            wait();
        }
        token=true;
    }
    public synchronized void drop(){
        token=false;
        notifyAll();
    }
}
