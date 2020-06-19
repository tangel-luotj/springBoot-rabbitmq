package com.tangel.rabbitmq.consumer.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.tangel.rabbitmq.utils.ConnectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 主题(通配符)模式 - 消费者1
 *
 * @author create by luotj
 * @Date: 2020/6/19 3:59 下午
 **/
@Slf4j
public class TopicMsgConsumer1 {

    /* 交换机名称 */
    private static final String EXCHANGE_NAME = "topic_exchange";
    /* 队列名称 */
    private static final String QUEUE_NAME = "topic_queue1";

    public static void main(String[] args) throws IOException, InterruptedException {
        //获取连接,创建通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //根据通道创建队列，并对交换机和队列进行绑定
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "topic.*");

        channel.basicQos(1);

        //监控消息
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);

        while (true) {
            //获取传送的消息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String body = new String(delivery.getBody());
            log.info("消息内容:{}", body);
            //线程睡眠1秒
            Thread.sleep(1000);
            //消息确认
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }

    }
}
