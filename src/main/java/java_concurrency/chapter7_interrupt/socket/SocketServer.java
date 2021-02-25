package java_concurrency.chapter7_interrupt.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author chen
 * @date 2021/2/21 19:32
 * @Description Socket服务端
 */
@Slf4j
public class SocketServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            for (; ; ) {
                Socket accept = serverSocket.accept(); // 阻塞
                log.info("服务端收到客户端连接，连接地址：{}", accept.getInetAddress().getHostAddress());
                InputStream inputStream = accept.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String tmp;
                /**
                 *  注意：该测试项中，一开始自己懵逼没搞懂为啥没收到socket客户端的数据，原来是因为客户端发送的时候没有换行符
                 *  reader.readLine() 是以换行符结尾的
                 *  while 的方式表示会一直读取客户端输入，如果此时客户端强行断开连接，服务端会侦测到并抛出异常
                 *  java.net.SocketException: Connection reset
                 */
                while ((tmp = reader.readLine()) != null) {
                    log.info("服务端收到客户端消息：{}", tmp);
                }
            }
        } catch (IOException e) {
            log.error("哎呀呀，服务端关闭连接啦");
        }
    }
}
