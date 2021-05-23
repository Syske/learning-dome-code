package io.github.syske.springboot.activemq.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

/**
 * @program: springboot-activemq-demo
 * @description: jms消息服务
 * @author: syske
 * @date: 2021-05-23 10:06
 */
@Service
public class JmsSendService {
    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    public void sendMessage(String queueName, String message) {
        jmsTemplate.convertAndSend(queueName, message);
    }

    public String sendAndReceive(String queueName, String message) {
        Message<?> messageBack = jmsTemplate.sendAndReceive(queueName, new StringMessage(message));
        return (String)messageBack.getPayload();
    }

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
