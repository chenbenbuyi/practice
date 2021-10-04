package skill.rabbitmq.workqueue;

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

        channel.queueDeclare("work-queue", true, false, false, null);

        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", "work-queue", MessageProperties.PERSISTENT_TEXT_PLAIN, (i + "任务队列消息").getBytes());
        }

        RabbitMQUtil.close(connection, channel);

    }
}
