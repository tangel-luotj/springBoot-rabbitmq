package com.tangel.rabbitmq.consumer.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式 - 消费者2
 *
 * @author create by luotj
 * @Date: 2020/6/19 10:31 上午
 **/
@Slf4j
public class FanoutMsgConsumer2 {

    /* 定义队列名称 */
    private static final String QUEUE_NAME = "fanout_queue2";

    /* 定义交换机名称 */
    private static final String EXCHANGE_NAME = "fanout_exchange";

    private static Connection queryConnection() throws IOException, TimeoutException {
        //连接配置-获取连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPassword("guest");
        factory.setUsername("guest");
        return factory.newConnection();
    }

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        //获取连接
        Connection connection = queryConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //定义队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //绑定交换机-队列
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
        //监控消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);
        //设置消息的最大吞吐量
        channel.basicQos(1);

        while (true) {
            //获取传输信息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String body = new String(delivery.getBody());
            log.info("获取的消息为:{}", body);
            //沉睡1秒
            Thread.sleep(1000);
            //消息确认
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }


}
