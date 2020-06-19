package com.tangel.template.consumer.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * work模式 - 2号消费者
 *
 * @author create by luotj
 * @Date: 2020/6/18 12:27 下午
 **/
@Slf4j
public class WorkMsgConsumer2 {

    /* 队列名称 */
    private static final String QUEUE_NAME = "workQueue1";

    private static Connection queryConnection() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("localhost");
        factory.setVirtualHost("/");
        factory.setPort(5672);
        return factory.newConnection();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //获取连接
        Connection connection = queryConnection();
        Channel channel = connection.createChannel();
        //绑定队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //接收消息的最大值
        channel.basicQos(1);
        //队列绑定消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //设置手动ack(Acknowledge)
        channel.basicConsume(QUEUE_NAME, false, consumer);

        while (true) {
            //获取传送的消息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            //获取消息
            String body = new String(delivery.getBody());
            log.info("2号消费者消费的消息:{}", body);
            //休眠0.01秒
            Thread.sleep(10);
            //手动ACK,ACK完毕接收下一条消息
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

}
