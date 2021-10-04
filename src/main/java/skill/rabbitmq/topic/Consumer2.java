package skill.rabbitmq.topic;

import com.rabbitmq.client.*;
import skill.rabbitmq.RabbitMQUtil;

import java.io.IOException;

/**
 * @Description 消息订阅者
 * @Date 2021/10/4 17:53
 * @Created by csxian
 *  和路由的方式主要不同在于可以通过通配符的形式配置路由key
 */
public class Consumer2 {
    private static final String EXCHANGE_NAME = "topic-exchange";

    public static void main(String[] args) throws IOException {
        String routeKey = "#";
        Connection connection = RabbitMQUtil.connection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, routeKey);

        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("错误日志消费者获取的消息：" + new String(body));
            }
        });
    }
}
