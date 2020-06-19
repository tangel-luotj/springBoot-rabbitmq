package com.tangel.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author create by luotj
 * @Date: 2020/6/6 7:05 下午
 **/
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@EnableCaching
@EnableAsync
@EnableScheduling
@ImportResource()
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).start();
    }

    @Bean
    public ThreadPoolTaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(64);
        executor.setQueueCapacity(65535);
        executor.setThreadNamePrefix("AsyncExecutor");
        executor.initialize();
        return executor;
    }

}
