package thinckingInJava.part18_io.p10_nio;

import java.io.FileOutputStream;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/1/2 0:14
 * @Description 文件加锁
 */
public class FileLocking {
    public static void main(String[] args) {
        /**
         * java在发现你正在尝试锁定的文件已经有锁的时候，会抛出 java.nio.channels.OverlappingFileLockException
         * 一般是多线程操作的时候出现了多个线程竞争同一个文件锁的状况，或者在一个线程内对同一个文件多次进行文件锁操作。
         */
        new Thread(() -> {
            try (FileOutputStream out = new FileOutputStream("D:\\test")) {
                // tryLock 尝试获取文件锁，还有包含时间的重载方法
                FileLock fileLock = out.getChannel().tryLock();
                if (fileLock != null) {
                    System.out.println(Thread.currentThread().getName() + "线程获取到文件锁");
                    TimeUnit.SECONDS.sleep(3);
                    fileLock.release();
                    System.out.println(Thread.currentThread().getName() + "线程已经释放文件锁");
                }
                out.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        try (FileOutputStream out = new FileOutputStream("D:\\test")) {
            FileLock fileLock = out.getChannel().tryLock();
            if (fileLock != null) {
                System.out.println("主线程获取到了文件锁");
                fileLock.release();
                System.out.println("主线程已经释放了文件锁");
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
