package skill.rabbitmq.topic;

import com.rabbitmq.client.*;
import skill.rabbitmq.RabbitMQUtil;

import java.io.IOException;

/**
 * @Description 消息订阅者
 * @Date 2021/10/4 17:53
 * @Created by csxian
 */
public class Consumer {
    private static final String EXCHANGE_NAME = "topic-exchange";

    public static void main(String[] args) throws IOException {
        String infRouteKey = "*.chen";
        Connection connection = RabbitMQUtil.connection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();
        // 绑定多个路由key
        channel.queueBind(queueName, EXCHANGE_NAME, infRouteKey);

        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("控制台消费者获取的消息：" + new String(body));
            }
        });
    }
}
