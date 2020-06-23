package com.tangel.rabbitmq.provider.simple;

import com.google.common.collect.Maps;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tangel.rabbitmq.utils.ConnectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 简单队列 之 生产者
 * desc: 生产者通过定义的队列名称，发布消息给消费者进行消费
 *
 * @author create by luotj
 * @Date: 2020/6/8 7:04 下午
 **/
@Slf4j
public class SimpleMsgProvider {
    /* 定义队列名称 */
    private static final String QUEUE_NAME = "simpleQueue1";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //创建信道
        Channel channel = connection.createChannel();

        channel.confirmSelect();

        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("hello", "rabbitmq");
        paramMap.put("type", "simple");
        SimpleResponse response = new SimpleResponse();
        response.setUserId(1L);
        response.setUserName("Tangel");
        paramMap.put("response", response.toString());

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .headers(paramMap)              //请求头参数
                .contentEncoding("utf-8")
                .expiration("10000")
                .deliveryMode(2)                //1:非持久性，2:持久性
                .build();

        //定义发布的信息
        String message = "hello , this is my first msg!!";
        //发布消息
        channel.basicPublish("", QUEUE_NAME, properties, message.getBytes());
    }

}
