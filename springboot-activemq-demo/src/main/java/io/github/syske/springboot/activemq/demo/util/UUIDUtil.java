package io.github.syske.springboot.activemq.demo.util;

import java.util.UUID;

/**
 * @program: springboot-activemq-demo
 * @description: uuid工具类
 * @author: syske
 * @date: 2021-05-23 10:21
 */
public class UUIDUtil {
    public static String getUUId() {
        return UUID.randomUUID().toString();
    }
}
