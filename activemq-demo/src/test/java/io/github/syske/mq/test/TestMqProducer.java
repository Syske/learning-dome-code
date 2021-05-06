package io.github.syske.mq.test;

import io.github.syske.mq.ActiveMqProducer;
import org.junit.jupiter.api.Test;

/**
 * 测试消息生产者
 *
 * @author syske
 * @version 1.0
 * @date 2021-04-24 上午10:26
 */
public class TestMqProducer {

    /**
     * 生产消息
     *
     * @throws Exception
     */
    @Test
    public void produceMessage() throws Exception {
        new ActiveMqProducer().mQProducerQueue();
    }
}
