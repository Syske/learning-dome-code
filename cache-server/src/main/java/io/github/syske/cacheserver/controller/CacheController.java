package io.github.syske.cacheserver.controller;

import io.github.syske.cacheserver.dto.Result;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: cache-server
 * @description: 缓存服务controller
 * @author: syske
 * @create: 2021-02-23 11:38
 */
@RestController
public class CacheController {


    /**
     * 缓存数据
     *
     * @param data 要缓存的数据，格式JsonString
     * @param expireTime 数据过期时间，值为NULL时表示数据不过期
     * @return
     */
    public Result saveData(String data, Long expireTime) {
        return new Result();
    }
}
