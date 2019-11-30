package io.github.syske.aop.util;

import java.util.UUID;

/**
 * @program: publicCoreServer
 * @description: UUID工具类
 * @author: liu yan
 * @create: 2019-11-22 09:19
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
