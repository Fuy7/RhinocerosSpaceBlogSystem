package com.fuy.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author fuy
 * @create 2020-07-02 0:27
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {
    @Bean
    public Executor asyncExecutor() {
        //创建线程池对象
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);    //初始线程个数
        executor.setMaxPoolSize(10);    //最大线程个数
        executor.setThreadNamePrefix("rhinoceros_blog_worker-");
        executor.setQueueCapacity(30);  //队列容量
        executor.initialize();
        //返回池对象
        return executor;
    }
}
