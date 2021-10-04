package skill.rabbitmq.pubsub;

import com.rabbitmq.client.*;
import skill.rabbitmq.RabbitMQUtil;

import java.io.IOException;

/**
 * @Description 消息订阅者
 * @Date 2021/10/4 17:53
 * @Created by csxian
 */
public class Consumer {
    private static final String EXCHANGE_NAME = "chen-exchange";

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtil.connection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1获取广播中的消息：" + new String(body));
            }
        });
    }
}
