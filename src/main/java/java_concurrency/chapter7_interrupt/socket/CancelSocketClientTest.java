package java_concurrency.chapter7_interrupt.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author chen
 * @date 2021/2/22 21:46
 * @Description 兼容非标准取消的socket
 */

public class CancelSocketClientTest {
    static final ExecutorService exec = new CancellingExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue(5));

    public static void main(String[] args) throws IOException, InterruptedException {
        CompatibilityCancel<String> stringCompatibilityCancel = new CompatibilityCancel<>();
        stringCompatibilityCancel.setSocket(new Socket("127.0.0.1", 9999));
        Future<String> future = exec.submit(stringCompatibilityCancel);
        TimeUnit.SECONDS.sleep(5); // 5 s后取消
        future.cancel(true);
        exec.shutdown();
    }
}
