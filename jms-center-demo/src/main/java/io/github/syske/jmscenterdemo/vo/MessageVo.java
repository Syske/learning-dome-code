/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.jmscenterdemo.vo;

import java.util.Objects;

/**
 * 消息体
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-10 15:46
 */
public class MessageVo {
    /**
     * 消息头
     */
    private MessageHeader messageHeader;
    /**
     * 消息体
     */
    private MessageBody messageBody;

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }

    /**
     * 构建消息头
     * @return
     */
    public MessageHeader messageHeader() {
        if (Objects.isNull(this.messageHeader)) {
            this.messageHeader = new MessageHeader();
        }
        return this.messageHeader;
    }

    /**
     * 构建消息体
     * @return
     */
    public MessageBody messageBody() {
        if (Objects.isNull(this.messageBody)) {
            this.messageBody = new MessageBody();
        }
        return this.messageBody;
    }

    public static class MessageHeader {
        /**
         * 消息类型
         */
        private Integer type;
        /**
         * 平台
         */
        private Integer platform;
        /**
         * 消息发送者
         */
        private String sender;

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

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }
    }

    public static class MessageBody {
        /**
         * 消息内容
         */
        private String messageContent;

        public String getMessageContent() {
            return messageContent;
        }

        public void setMessageContent(String messageContent) {
            this.messageContent = messageContent;
        }
    }

}
