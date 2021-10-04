package skill.rabbitmq.route;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import skill.rabbitmq.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description RabbitMQ 消息生产者
 * @Date 2021/10/4 12:25
 * @Created by csxian
 */
public class Producer {
    private static final String EXCHANGE_NAME = "log-exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtil.connection();
        // 获取连接中的通道
        Channel channel = connection.createChannel();
        // 绑定交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String routeKey = "error";
        for (int i = 0; i < 3; i++) {
            channel.basicPublish(EXCHANGE_NAME, routeKey, MessageProperties.PERSISTENT_TEXT_PLAIN, ("日志系统产生的"+routeKey+"等级消息").getBytes());
        }

        RabbitMQUtil.close(connection, channel);

    }
}
