package com.tangel.rabbitmq.controller.ui;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 正则表达式测试类
 * @author create by luotj
 * @Date: 2020/6/19 5:07 下午
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests {

    @Test
    public void test(){
        String value = "abc123456def";
        String pattern  = "/[0-9]/+";
        log.info("匹配结果:{}", value.matches(pattern));
    }

}
