package com.tangel.rabbitmq.consumer.topic;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author create by luotj
 * @Date: 2020/6/20 12:05 下午
 **/
@Slf4j
public class MyConsumer extends DefaultConsumer {

    private Channel mChannel;

    public MyConsumer(Channel channel) {
        super(channel);
        this.mChannel = channel;
    }

    @SneakyThrows
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        log.info("consumerTag:{}", consumerTag);
        log.info("envelope:{}", envelope);
        log.info("properties:{}", properties);
        log.info("body:{}", new String(body));
        mChannel.basicAck(envelope.getDeliveryTag(), false);
        Thread.sleep(5000);
    }
}
