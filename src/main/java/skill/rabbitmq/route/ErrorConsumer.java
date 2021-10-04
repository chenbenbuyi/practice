package skill.rabbitmq.route;

import com.rabbitmq.client.*;
import skill.rabbitmq.RabbitMQUtil;

import java.io.IOException;

/**
 * @Description 消息订阅者
 * @Date 2021/10/4 17:53
 * @Created by csxian
 */
public class ErrorConsumer {
    private static final String EXCHANGE_NAME = "log-exchange";

    public static void main(String[] args) throws IOException {
        String routeKey = "error";
        Connection connection = RabbitMQUtil.connection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
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
