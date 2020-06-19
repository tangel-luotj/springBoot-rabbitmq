package com.tangel.rabbitmq.provider.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tangel.rabbitmq.utils.ConnectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 主题(通配符)模式 - 生产者
 *
 * @author create by luotj
 * @Date: 2020/6/19 3:59 下午
 **/
@Slf4j
public class TopicMsgProvider {

    /* 交换机名称 */
    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws IOException {
        //获取连接、创建通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //定义topic类型交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        //对交换机发送消息
        String message = "hello i am a topic message1!!";
        channel.basicPublish(EXCHANGE_NAME, "topic.tangel", null, message.getBytes());
        String msg = "hello i am a topic message2!!";
        channel.basicPublish(EXCHANGE_NAME, "topic.luotj.tangel.memetest", null, msg.getBytes());
        //关闭连接
        channel.close();
        connection.close();
    }

}
