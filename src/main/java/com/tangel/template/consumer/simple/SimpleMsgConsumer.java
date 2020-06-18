package com.tangel.template.consumer.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.tangel.template.utils.ConnectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 简单队列 之 消费者
 *  desc: 消费者通过定义的队列名称，消费对应未被消费的消息
 *
 * @author create by luotj
 * @Date: 2020/6/17 6:24 下午
 **/
@Slf4j
public class SimpleMsgConsumer {

    private static final String QUEUE_NAME = "simpleQueue1";

    public static void main(String[] args) throws IOException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true) {
            //获取生产者发布的消息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            log.info("简单队列消费者获取信息:{}", msg);
        }
    }

}
