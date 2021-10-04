package skill.rabbitmq;

import cn.hutool.core.lang.Assert;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @Description
 * @Date 2021/10/4 14:55
 * @Created by csxian
 */
public class RabbitMQUtil {
    private static ConnectionFactory connectionFactory;

    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.43.122");
        connectionFactory.setPort(5672);
        // 设置连接的虚拟主机。 一个虚拟机主机类似于一个数据库，不同的应用程序可以设置不同的虚拟主机
        connectionFactory.setVirtualHost("/chen");
        connectionFactory.setUsername("chenbenbuyi");
        connectionFactory.setPassword("123456");
    }

    // 获取连接对象
    public static Connection connection() {
        try {
            Connection connection = connectionFactory.newConnection();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection connection, Channel channel) {
        Assert.notNull(connection);
        Assert.notNull(channel);
        try {
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
