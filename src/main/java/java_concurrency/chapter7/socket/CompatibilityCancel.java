package java_concurrency.chapter7.socket;

import cn.hutool.core.util.StrUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @author chen
 * @date 2021/2/20 22:02
 * @Description 将非标准的取消操作（如阻塞的I/O操作）通过任务取消的方式进行兼容性封装，实现既能支持标准的取消操作，又能响应非标准的取消操作
 */
public class CompatibilityCancel<T> implements CancellableTask<T> {

    private Socket socket;

    public synchronized void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public synchronized void cancel() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this) {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    System.out.println("关闭socket");
                    CompatibilityCancel.this.cancel();
                } finally {
                    System.out.println("一定会执行的中断操作");
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }

    @Override
    public T call() throws Exception {
        OutputStream os = socket.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.next();
            if (StrUtil.isNotEmpty(str)) {
                writer.write(str + System.lineSeparator());
                writer.flush();
            }
        }
        return null;
    }
}



