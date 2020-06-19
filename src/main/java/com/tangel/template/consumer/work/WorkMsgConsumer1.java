package com.tangel.template.consumer.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * work模式 - 1号消费者
 *
 * @author create by luotj
 * @Date: 2020/6/18 12:27 下午
 **/
@Slf4j
public class WorkMsgConsumer1 {

    private static final String QUEUE_NAME = "workQueue1";

    private static Connection queryConnection() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setHost("127.0.0.1");
        factory.setPassword("guest");
        factory.setUsername("guest");
        return factory.newConnection();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //获取连接配置
        Connection connection = queryConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //接收消息的最大值
        channel.basicQos(1);
        //队列消费绑定
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //设置手动的Ack，接收到确认之后，重新消费新的消息
        channel.basicConsume(QUEUE_NAME, false, consumer);

        while (true) {
            //获取传输信息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            //获取消费实体
            String body = new String(delivery.getBody());
            log.info("1号消费者消费的消息:{}", body);
            //沉睡1秒
            Thread.sleep(1000);
            //使用手动确认模式
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

}
