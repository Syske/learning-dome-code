/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.jmscenterdemo;

import com.alibaba.fastjson.JSON;
import io.github.syske.jmscenterdemo.common.MessagePlatformEnum;
import io.github.syske.jmscenterdemo.common.MessageTypeEnum;
import io.github.syske.jmscenterdemo.vo.MessageVo;
import org.junit.jupiter.api.Test;

/**
 * jms测试
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-10 16:34
 */
public class JmsTest {

    @Test
    public void createMessage() {
        MessageVo messageVo = new MessageVo();
        MessageVo.MessageBody messageBody = messageVo.messageBody();
        messageBody.setMessageContent("hello message center");
        MessageVo.MessageHeader messageHeader = messageVo.messageHeader();
        messageHeader.setPlatform(MessagePlatformEnum.DingDing.value());
        messageHeader.setSender("syske");
        messageHeader.setType(MessageTypeEnum.Text.value());
        System.out.println(JSON.toJSONString(messageVo));
    }
}
