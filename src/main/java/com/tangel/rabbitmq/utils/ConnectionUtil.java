package com.tangel.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * 获取连接
 * @author create by luotj
 * @Date: 2020/6/8 7:11 下午
 **/
public class ConnectionUtil {

    public static Connection getConnection() throws IOException {
        //创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        //配置账号、密码、主机、虚拟主机、端口号
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("127.0.0.1");
        factory.setVirtualHost("/");
        factory.setPort(5672);
        //创建连接
        return factory.newConnection();
    }

}
