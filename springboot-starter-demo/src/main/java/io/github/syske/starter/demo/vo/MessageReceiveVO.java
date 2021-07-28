/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.starter.demo.vo;

/**
 * 有返回消息出参
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-18 11:33
 */
public class MessageReceiveVO {
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

    public static class MessageHeader {
        private Boolean success;
        private String errorMessage;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
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
