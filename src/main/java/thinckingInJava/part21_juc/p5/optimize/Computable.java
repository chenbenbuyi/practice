package thinckingInJava.part21_juc.p5.optimize;

/**
 * @author chen
 * @date 2021/1/24 22:35
 */
public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
