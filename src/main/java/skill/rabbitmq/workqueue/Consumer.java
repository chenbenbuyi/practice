package skill.rabbitmq.workqueue;

import com.rabbitmq.client.*;
import skill.rabbitmq.RabbitMQUtil;

import java.io.IOException;

/**
 * @Description RabbitMQ 消息消费者
 * @Date 2021/10/4 12:25
 * @Created by csxian
 * 默认情况下，每个消费者会受到平均数量的消息，参考官网（https://www.rabbitmq.com/tutorials/tutorial-two-java.html）的说明
 * By default, RabbitMQ will send each message to the next consumer, in sequence. On average every consumer will get the same number of messages.
 * This way of distributing messages is called round-robin. Try this out with three or more workers
 */
public class Consumer {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtil.connection();
        // 获取连接中的通道
        Channel channel = connection.createChannel();
        channel.basicQos(1);
        channel.queueDeclare("jenkins.task.tool.queue", false, false, false, null);

        channel.basicConsume("jenkins.task.tool.queue", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1获取的工作队列中的消息：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
