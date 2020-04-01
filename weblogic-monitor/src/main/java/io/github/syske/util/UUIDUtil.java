package io.github.syske.util;

import java.util.Random;
import java.util.UUID;

/**
 * @program: weblogic-monitor
 * @description: UUID工具类
 * @author: syske
 * @create: 2020-01-19 18:03
 */
public class UUIDUtil {
    /**
     * 获取UUID
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
