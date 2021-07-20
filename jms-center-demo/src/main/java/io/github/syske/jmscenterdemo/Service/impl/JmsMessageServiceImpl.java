/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.jmscenterdemo.Service.impl;

import io.github.syske.jmscenterdemo.Service.JmsMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * 消息中心服务实现
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-10 16:21
 */
@Service
public class JmsMessageServiceImpl implements JmsMessageService {
    private final Logger logger = LoggerFactory.getLogger(JmsMessageServiceImpl.class);

    @Override
    @JmsListener(destination = "${mq.queue.main.message}")
    public void distributeMessage(String message) {
        logger.info("method: [distributeMessage] input parameter: {}", message);

    }
}
