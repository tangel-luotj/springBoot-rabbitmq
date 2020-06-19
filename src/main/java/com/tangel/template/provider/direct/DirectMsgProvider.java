package com.tangel.template.provider.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 路由模式 - 生产者
 *
 * @author create by luotj
 * @Date: 2020/6/19 2:11 下午
 **/
@Slf4j
public class DirectMsgProvider {

    /* 交换机名称 */
    private static final String EXCHANGE_NAME = "direct_exchange";

    private static Connection queryConnection() throws IOException {
        //配置连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        factory.setHost("127.0.0.1");
        factory.setVirtualHost("/");
        return factory.newConnection();
    }

    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = queryConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String directMsg = "hello , i am direct message!!";
        //发送消息，设置路由key为"fail"
        channel.basicPublish(EXCHANGE_NAME, "fail", null, directMsg.getBytes());
        log.info("路由消息:{}", directMsg);
        //关闭连接
        channel.close();
        connection.close();
    }
}
