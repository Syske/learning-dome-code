package io.github.syske.cacheserver.service.impl;

import io.github.syske.cacheserver.dto.CacheRequestDTO;
import io.github.syske.cacheserver.dto.Result;
import io.github.syske.cacheserver.service.CacheService;
import io.github.syske.cacheserver.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @program: cache-server
 * @description: 缓存业务实现
 * @author: syske
 * @create: 2021-02-23 11:51
 */
@CacheConfig(cacheNames = "redis_test")
@Service
public class CacheServiceImpl implements CacheService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtil redisUtil;

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

    @Override
    public Result<String> cacheSingleObjectData(CacheRequestDTO<String> cacheRequestDTO) {
        Long expireTime = cacheRequestDTO.getExpireTime();
        String key = cacheRequestDTO.getKey();
        String data = cacheRequestDTO.getData();
        logger.debug("缓存数据：key:{},data:{}" ,key , data);
        boolean setSuccess;
        if(expireTime == null) {
            setSuccess = redisUtil.set(key, cacheRequestDTO.getData());
        } else {
            setSuccess = redisUtil.set(key, data, expireTime);
        }
        return setSuccess ? new Result(cacheRequestDTO.getData()): Result.getFailed("数据缓存失败");
    }

    @Override
    public Result<List> cacheListData(CacheRequestDTO<List> cacheRequestDTO) {
        Long expireTime = cacheRequestDTO.getExpireTime();
        String key = cacheRequestDTO.getKey();
        List data = cacheRequestDTO.getData();
        boolean setSuccess;
        if(expireTime == null) {
            setSuccess = redisUtil.lSet(key, cacheRequestDTO.getData());
        } else {
            setSuccess = redisUtil.lSet(key, data, expireTime);
        }
        return setSuccess ? new Result(cacheRequestDTO.getData()): Result.getFailed("数据缓存失败");
    }

    @Override
    public Result deleteCacheData(CacheRequestDTO<Set> cacheRequestDTO) {
        String key = cacheRequestDTO.getKey();
        if(redisUtil.exists(key)) {
            redisUtil.del(key);
        }
        return new Result();
    }

    @Override
    public Result setCacheDataExpireTime(CacheRequestDTO cacheRequestDTO) {
        String key = cacheRequestDTO.getKey();
        boolean expireSuccess = false;
        if (redisUtil.exists(key)) {
            expireSuccess = redisUtil.expire(key, cacheRequestDTO.getExpireTime());
        }
        return expireSuccess ?  new Result(cacheRequestDTO.getData()): Result.getFailed("设置过期时间失败");
    }

    @Override
    public Result getCacheDataExpireTime(CacheRequestDTO cacheRequestDTO) {
        String key = cacheRequestDTO.getKey();
        Long expireTime = null;
        if (redisUtil.exists(key)) {
            expireTime = redisUtil.getExpire(key);
        }
        return new Result(expireTime);
    }

    @Override
    public Result getCacheData(CacheRequestDTO cacheRequestDTO) {
        String key = cacheRequestDTO.getKey();
        Object expireSuccess = null;
        if (redisUtil.exists(key)) {
            expireSuccess = redisUtil.get(key);
        }
        return new Result(expireSuccess);
    }

    @Override
    public Result listCacheData(CacheRequestDTO cacheRequestDTO) {
        String key = cacheRequestDTO.getKey();
        List listData = null;
        if (redisUtil.exists(key)) {
            long lStartIndex;
            Long listStartIndex = cacheRequestDTO.getListStartIndex();
            if (listStartIndex == null) {
                lStartIndex = 0L;
            } else {
                lStartIndex = listStartIndex;
            }

            long lEndIndex;
            long listSize = redisUtil.lGetListSize(key);
            Long listEndIndex = cacheRequestDTO.getListEndIndex();
            if (listStartIndex == null) {
                lEndIndex = listSize;
            } else {
                lEndIndex = listEndIndex;
            }
            listData = redisUtil.lGet(key, lStartIndex, lEndIndex);
        }
        return new Result(listData);
    }

    @Override
    public Result exists(CacheRequestDTO cacheRequestDTO) {
        String key = cacheRequestDTO.getKey();
        boolean resut = redisUtil.exists(key);
        return new Result(resut);
    }
}
