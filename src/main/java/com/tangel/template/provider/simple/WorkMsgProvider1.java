package com.tangel.template.provider.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * work模式 - 消息提供者
 *
 * @author create by luotj
 * @Date: 2020/6/18 12:27 下午
 **/
@Slf4j
public class WorkMsgProvider1 {

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

    public static void main(String[] args) throws IOException {
        Connection connection = queryConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 1; i <= 50; i++) {
        String msg = "i am work queue Message!! index = " + i;
//        String msg = "i am work queue Message!!";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }
        channel.close();
        connection.close();
    }

}