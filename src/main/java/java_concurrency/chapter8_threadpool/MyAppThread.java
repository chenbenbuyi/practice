package java_concurrency.chapter8_threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chen
 * @date 2021/2/27 12:52
 */

@Slf4j
public class MyAppThread extends Thread {
    private static final String DEFAULT_NAME = "布衣线程";
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();

    MyAppThread(Runnable r) {
        this(r, DEFAULT_NAME);
    }

    MyAppThread(Runnable r, String name) {
        super(r, name + "-" + created.incrementAndGet());
        super.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                log.info("线程 {} 抛出了异常，异常信息", t.getName(), e);
            }
        });
    }

    @Override
    public void run() {
        log.info("创建了 {} 线程>>>>>>>>>>>>", getName());
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            log.info(" {} 执行完毕<<<<<<<<<<<.", getName());
        }
    }

}
