/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.starter.demo.service.impl;

import com.alibaba.fastjson.JSON;
import io.github.syske.starter.demo.service.JmsMessageService;
import io.github.syske.starter.demo.vo.MessageReceiveVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * 消息中心服务实现
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-10 16:21
 */
public class JmsMessageServiceImpl implements JmsMessageService {
    private final Logger logger = LoggerFactory.getLogger(JmsMessageServiceImpl.class);

    private JmsMessagingTemplate jmsTemplate;

    public JmsMessageServiceImpl(JmsMessagingTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }



    @Override
    public void sendMessage(String mqQueueName, String message) {
        logger.info("method: [sendMessage] input parameter: mqQueueName = {}， message = {}", mqQueueName, message);
        jmsTemplate.convertAndSend(mqQueueName, message);
    }

    @Override
    public MessageReceiveVO sendAndReceive(String mqQueueName, String message) {
        logger.info("method: [sendMessage] input parameter: mqQueueName = {}， message = {}", mqQueueName, message);
        Message<?> messageBack = jmsTemplate.sendAndReceive(mqQueueName, new StringMessage(message));
        String payload = (String) messageBack.getPayload();
        logger.info("method: [sendMessage] return result: payload = {}", payload);
        return JSON.parseObject(payload, MessageReceiveVO.class);
    }

    class StringMessage implements Message<String> {

        private String payload;
        private MessageHeaders messageHeaders;

        public StringMessage(String payload) {
            this.payload = payload;
        }

        @Override
        public String getPayload() {
            return this.payload;
        }

        @Override
        public MessageHeaders getHeaders() {
            return this.messageHeaders;
        }
    }
}
