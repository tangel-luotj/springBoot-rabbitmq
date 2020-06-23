package com.tangel.rabbitmq.consumer.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tangel.rabbitmq.utils.ConnectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题(通配符)模式 - 消费者2
 *
 * @author create by luotj
 * @Date: 2020/6/19 3:59 下午
 **/
@Slf4j
public class TopicMsgConsumer2 {

    /* 交换机名称 */
    private static final String EXCHANGE_NAME = "topic_exchange";
    /* 队列名称 */
    private static final String QUEUE_NAME = "topic_queue2";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接,创建通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //根据通道创建队列，并对交换机和队列进行绑定
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "topic.#");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "topic.luotj.*");

        channel.basicQos(1);

        //监控消息
        channel.basicConsume(QUEUE_NAME, false, new MyConsumer(channel));
    }
}
