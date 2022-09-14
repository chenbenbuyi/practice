package skill.rabbitmq.helloword;

import com.rabbitmq.client.AMQP;
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
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtil.connection();
        // 获取连接中的通道
        Channel channel = connection.createChannel();

        /**
         * 绑定通道中的消息队列
         * String queue 队列名称
         * boolean durable 队列是否要持久化 注意，只是队列持久化不是消息的持久化，消息的持久化是在发布消息的时候指定的
         * boolean exclusive 是否独占队列
         * boolean autoDelete 是否在消费者连接断开后自动删除队列
         * Map<String, Object> arguments  额外参数
         */
        channel.queueDeclare("chen-queue", false, false, false, null);

        for (int i = 0; i < 1; i++) {
            channel.basicPublish("", "chen-queue", MessageProperties.PERSISTENT_BASIC, ("测试 认证").getBytes());
        }

        RabbitMQUtil.close(connection, channel);

    }
}
