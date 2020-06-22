package com.tangel.rabbitmq.provider.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式 - 生产者
 *
 *
 * @author create by luotj
 * @Date: 2020/6/19 10:38 上午
 **/
@Slf4j
public class FanoutMsgProvider {

    private static final String EXCHANGE_NAME = "fanout_exchange";

    private static Connection queryConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("127.0.0.1");
        factory.setVirtualHost("/");
        factory.setPort(5672);
        return factory.newConnection();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = queryConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        for (int i = 1; i <= 50; i++) {
            String msg = "i am fanout msg!! index = " + i;
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
            log.info("消息发送:{}", msg);
        }
        channel.close();
        connection.close();
    }

}
