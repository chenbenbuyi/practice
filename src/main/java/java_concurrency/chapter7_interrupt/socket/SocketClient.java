package java_concurrency.chapter7_interrupt.socket;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author chen
 * @date 2021/2/21 19:32
 * @Description socket 客户端
 */

@Slf4j
public class SocketClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 9999)) {
            OutputStream os = socket.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String sout = scanner.next();
                if (StrUtil.isNotEmpty(sout)) {
                    writer.write(sout + System.lineSeparator());
                    writer.flush();
                }
            }
        } catch (IOException e) {
            log.error("哎呀呀，客户端关闭连接啦");
        }
    }
}
