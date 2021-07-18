/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.jmscenterdemo.common;

/**
 * 消息类型枚举
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-10 16:11
 */
public enum MessageTypeEnum {
    /**
     * 文本消息
     */
    Text(1),
    /**
     * oa消息
     */
    OA(2),
    /**
     * 事件消息
     */
    Event(3);
    /**
     * 消息类型id
     */
    private Integer typeId;
    MessageTypeEnum(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer value() {
        return typeId;
    }
}
