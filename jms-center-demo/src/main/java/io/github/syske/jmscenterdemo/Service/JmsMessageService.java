/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.jmscenterdemo.Service;

/**
 * 消息服务
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-10 15:14
 */
public interface JmsMessageService {

    /**
     * 消息分发
     * @param message
     */
    void distributeMessage(String message);
}
