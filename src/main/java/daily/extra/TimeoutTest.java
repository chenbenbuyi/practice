package daily.extra;

import lombok.extern.slf4j.Slf4j;

/**
 * @date: 2020/12/1 10:39
 * @author: chen
 * @desc: 根据Thread 的join源码，模拟超时接口的编写
 */
@Slf4j
public class TimeoutTest {

    public static void main(String[] args) {
        TimeoutTest timeoutInter = new TimeoutTest();
//        timeoutInter.fun1();
        timeoutInter.fun2(5000);
    }

    public void fun1() {
        log.info("普通接口,一直阻塞");
        while (true) {}
    }

    /**
     * @param timeout 毫秒
     */
    public void fun2(long timeout) {
        long base = System.currentTimeMillis();
        long now = 0;
        while (true) {
            long delay = timeout - now;
            if (delay <= 0) {
               throw new RuntimeException("接口调用超时！");
            }
            now = System.currentTimeMillis() - base;
        }
    }
}

