package io.github.syske.mq.test;

import io.github.syske.mq.ActiveMqConsumer;
import org.junit.jupiter.api.Test;

/**
 * 测试消息消费者
 *
 * @author syske
 * @version 1.0
 * @date 2021-04-24 上午10:25
 */
public class TestMqConsumer {


    /**
     * 消费消息
     * @throws Exception
     */
    @Test
    public void testConsumeMessage() throws Exception {
        new ActiveMqConsumer().mQConsumerQueue();
    }
}
