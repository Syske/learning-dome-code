/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.jmscenterdemo.dao.entity;

import java.util.Date;

/**
 * 消息
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-10 16:00
 */
public class Message {
    /**
     * 消息类型
     */
    private Integer type;
    /**
     * 平台
     */
    private Integer platform;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 消息发送者
     */
    private String sender;
    /**
     * 消息创建时间
     */
    private Date createTime;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
