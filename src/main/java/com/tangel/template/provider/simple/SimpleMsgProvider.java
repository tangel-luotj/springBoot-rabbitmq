package com.tangel.template.provider.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tangel.template.utils.ConnectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 简单队列 之 生产者
 *      desc: 生产者通过定义的队列名称，发布消息给消费者进行消费
 * @author create by luotj
 * @Date: 2020/6/8 7:04 下午
 **/
@Slf4j
public class SimpleMsgProvider {
    /* 定义队列名称 */
    private static final String QUEUE_NAME = "testQueue1";

    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //定义发布的信息
        String message = "hello , this is my first msg!!";
        //发布消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        log.info("success send : {}", message);
        //关闭连接
        channel.close();
        connection.close();
    }

}
