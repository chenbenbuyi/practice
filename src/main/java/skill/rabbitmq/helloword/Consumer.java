package skill.rabbitmq.helloword;

import com.rabbitmq.client.*;
import skill.rabbitmq.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description RabbitMQ 消息消费者
 * @Date 2021/10/4 12:25
 * @Created by csxian
 */
public class Consumer {
    public static void main(String[] args) throws IOException{
        Connection connection = RabbitMQUtil.connection();
        // 获取连接中的通道
        Channel channel = connection.createChannel();

        /**
         * 绑定通道中的消息队列
         * String queue 队列名称
         * boolean durable 队列是否要持久化
         * boolean exclusive 是否独占队列
         * boolean autoDelete 是否在消费完成后自动删除队列
         * Map<String, Object> arguments  额外参数
         */
        channel.queueDeclare("chen-queue", false, false, false, null);

        channel.basicConsume("chen-queue", true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("获取的队列中的消息："+new String(body));
            }
        });
    }
}
