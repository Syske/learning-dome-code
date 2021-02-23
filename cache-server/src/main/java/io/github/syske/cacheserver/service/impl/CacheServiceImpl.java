package io.github.syske.cacheserver.service.impl;

import io.github.syske.cacheserver.service.CacheService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @program: cache-server
 * @description: 缓存业务实现
 * @author: syske
 * @create: 2021-02-23 11:51
 */
@CacheConfig(cacheNames = "redis_test")
@Service
public class CacheServiceImpl implements CacheService {
    /**
     * 缓存时间，首次查询后会缓存结果,key中的值可使用表达式计算
     *
     * @return long
     */
    @Cacheable(key = "'currentTime'")
    public long getTime() {
        return System.currentTimeMillis();
    }

    @CacheEvict(key = "'currentTime'")
    public void deleteTime() {

    }

    @Cacheable(key = "'currentTime'+#id")
    public Long getTime(String id) {
        return null;
    }

    /**
     * 一般用于更新查插入操作，每次都会请求db
     */
    @CachePut(key = "'currentTime'+#id")
    public long updateTime(String id) {
        return System.currentTimeMillis();
    }

    /**
     * 清除缓存
     *
     * @param id id
     */
    @CacheEvict(key = "'currentTime'+#id", allEntries = false)
    public void deleteTime(String id) {
    }
}
