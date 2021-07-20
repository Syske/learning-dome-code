/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.starter.demo.config;

import io.github.syske.starter.demo.service.JmsMessageService;
import io.github.syske.starter.demo.service.impl.JmsMessageServiceImpl;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.ConnectionFactory;

/**
 * 自动配置类
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-18 14:52
 */
@Configuration
@EnableJms
@ConditionalOnClass(JmsMessageServiceImpl.class)
public class AutoConfigurationClass {

    @Value("${spring.activemq.broker-url}")
    private String brokerURL;

    @ConditionalOnMissingBean
    @Bean
    public JmsMessageService jmsMessageService(JmsMessagingTemplate jmsTemplate){
        return new JmsMessageServiceImpl(jmsTemplate);
    }

    @ConditionalOnMissingBean
    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate(ConnectionFactory connectionFactory) {
        return new JmsMessagingTemplate(connectionFactory);
    }

    @ConditionalOnMissingBean
    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(brokerURL);
    }
}