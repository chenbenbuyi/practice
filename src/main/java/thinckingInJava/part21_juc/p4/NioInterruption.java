package thinckingInJava.part21_juc.p4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/7/2 6:58
 * @Description
 */
class NioBlocked implements Runnable {

    private final SocketChannel sc;

    public NioBlocked(SocketChannel sc) {
        this.sc = sc;
    }

    @Override
    public void run() {
        System.out.println("等待输入。。。");
        try {
            sc.read(ByteBuffer.allocate(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("结束当前阻塞线程");
    }


}

public class NioInterruption {
    public static void main(String[] args) throws IOException, InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 100, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        new ServerSocket(8080);
        InetSocketAddress localhost = new InetSocketAddress("localhost", 8080);
        SocketChannel sc1 = SocketChannel.open(localhost);
        SocketChannel sc2 = SocketChannel.open(localhost);
        Future<?> future = executor.submit(new NioBlocked(sc1));
        executor.submit(new NioBlocked(sc2));
        // https://www.jianshu.com/p/5b8451cc6a3a
        executor.shutdown();
        TimeUnit.SECONDS.sleep(1);
        // 设置中断标识来中断线程 当正在操作这个channel的线程被中断，则会抛出ClosedByInterruptException 异常
        future.cancel(true);
        TimeUnit.SECONDS.sleep(1);
        // 当在某个信道的 I/O 操作中处于阻塞状态的某个线程被另一个线程中断时，该线程将收到此经过检查的异常
        sc2.close(); // 通过关闭底层资源来中断线程
    }
}
