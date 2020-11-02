package thinckingInJava.part21_juc.p3;

/**
 * @author chen
 * @date 2020/5/17 8:40
 * @Description 产生序列数字
 */
public class SerialNumberGenerator {
    private static volatile int SerialNumber = 0;

    // 时刻注意，在多线程环境中，java的自增自减操作都不是线程安全的
    public static int nextSerialNumber() {
        return SerialNumber++;
    }
}
