/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.starter.demo.service;

import io.github.syske.starter.demo.vo.MessageReceiveVO;

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
     * @param mqQueueName 消息队列名称
     * @param message 消息内容
     */
    void sendMessage(String mqQueueName, String message);

    /**
     * 发送消息并接收返回值
     * @param queueName
     * @param message
     * @return
     */
    MessageReceiveVO sendAndReceive(String queueName, String message);
}
