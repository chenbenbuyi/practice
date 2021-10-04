package skill.rabbitmq.workqueue;

import ch.qos.logback.core.util.TimeUtil;
import com.rabbitmq.client.*;
import lombok.SneakyThrows;
import skill.rabbitmq.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Description RabbitMQ 消息消费者
 * @Date 2021/10/4 12:25
 * @Created by csxian
 */
public class Consumer2 {
    public static void main(String[] args) throws IOException{
        Connection connection = RabbitMQUtil.connection();
        // 获取连接中的通道
        Channel channel = connection.createChannel();

        channel.queueDeclare("work-queue", true, false, false, null);
        channel.basicQos(1);
        /**
         * autoAck 消息是否自动确认，设置为 true 表示自动确认。自动确认会造成消息的平均分配，如果消费者挂掉，会导致挂掉的消费者消费的消息丢失
         */
        channel.basicConsume("work-queue", false, new DefaultConsumer(channel){
            @SneakyThrows
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("消费者2获取的工作队列中的消息："+new String(body));
                // 确认的消息和是否开启多条确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
