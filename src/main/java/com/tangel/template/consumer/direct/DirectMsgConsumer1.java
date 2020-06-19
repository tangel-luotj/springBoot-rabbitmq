package com.tangel.template.consumer.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.tangel.template.utils.ConnectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 路由模式 - 消费者
 *
 * @author create by luotj
 * @Date: 2020/6/19 2:12 下午
 **/
@Slf4j
public class DirectMsgConsumer1 {

    private static final String EXCHANGE_NAME = "direct_exchange";

    private static final String QUEUE_NAME = "direct_queue1";

    public static void main(String[] args) throws IOException, InterruptedException {
        //获取连接和创建信道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //定义队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //一个队列可以绑定多个路由key
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "success");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "fail");
        //监控消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //设置最大吞吐量
        channel.basicQos(1);
        channel.basicConsume(QUEUE_NAME, false, consumer);

        while (true) {
            //获取传送的消息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String body = new String(delivery.getBody());
            log.info("消费信息:{}", body);
            //消息确认
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
