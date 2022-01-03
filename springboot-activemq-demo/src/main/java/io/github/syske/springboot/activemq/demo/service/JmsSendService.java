package io.github.syske.springboot.activemq.demo.service;

import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * @program: springboot-activemq-demo
 * @description: jms消息服务
 * @author: syske
 * @date: 2021-05-23 10:06
 */
@Service
public class JmsSendService {
    private final Logger logger = LoggerFactory.getLogger(JmsSendService.class);
    @Autowired
    private JmsMessagingTemplate jmsTemplate;
    
    @Autowired
    private JmsTemplate JmsBaseTemplate;

    public void sendMessage(String queueName, String message) {
        logger.info("deliveryMode: {}", jmsTemplate.getJmsTemplate().getDeliveryMode());
        logger.info("deliveryDelay: {}", jmsTemplate.getJmsTemplate().getDeliveryDelay());
        jmsTemplate.convertAndSend(queueName, message);
    }

    public String sendAndReceive(String queueName, String message) {
        Message<?> messageBack = jmsTemplate.sendAndReceive(queueName, new StringMessage(message));
        return (String)messageBack.getPayload();
    }

    public void sendDelayMessage2(String queueName, String messageInfo) {
        JmsBaseTemplate.send(new ActiveMQQueue(queueName), session -> {
            ObjectMessage message = session.createObjectMessage(messageInfo);
            //设置延迟时间
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 60*1000L);
            return message;
        });
    }

    public void sendDelayMessage(String queueName, String messageInfo) {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        // 获取连接工厂
        ConnectionFactory connectionFactory = jmsTemplate.getConnectionFactory();
        try {
            // 获取连接
            connection = connectionFactory.createConnection();
            connection.start();
            // 获取session，true开启事务，false关闭事务
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            // 创建一个消息队列
            producer = session.createProducer(new ActiveMQQueue(queueName));
            producer.setDeliveryMode(JmsProperties.DeliveryMode.PERSISTENT.getValue());
            ObjectMessage message = session.createObjectMessage(messageInfo);
            //设置延迟时间
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 60*1000L);
            // 发送消息
            producer.send(message);
            logger.info("发送消息：{}", messageInfo);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (producer != null) {
                    producer.close();
                }
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
