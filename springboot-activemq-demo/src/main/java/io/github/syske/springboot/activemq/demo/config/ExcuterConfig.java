package io.github.syske.springboot.activemq.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @program: springboot-activemq-demo
 * @description: 线程池配置
 * @author: syske
 * @date: 2021-05-23 9:58
 */
@Configuration
public class ExcuterConfig {
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(150);
        executor.setQueueCapacity(500);
        return executor;
    }
}
