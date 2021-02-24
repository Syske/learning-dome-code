package io.github.syske.cacheserver.service;

import io.github.syske.cacheserver.dto.CacheRequestDTO;
import io.github.syske.cacheserver.dto.Result;

import java.util.List;
import java.util.Set;

/**
 *  Demo class
 *
 * @author syske
 * @date 2021/02/24
 */
public interface CacheService {

    /**
     * 缓存strign数据
     * @param cacheRequestDTO
     * @return
     */
    Result<String> cacheSingleObjectData(CacheRequestDTO<String> cacheRequestDTO);

    /**
     * 缓存list数据
     * @param cacheRequestDTO
     * @return
     */
    Result<List> cacheListData(CacheRequestDTO<List> cacheRequestDTO);

    /**
     * 删除缓存数据
     * @param cacheRequestDTO
     * @return
     */
    Result deleteCacheData(CacheRequestDTO<Set> cacheRequestDTO);

    /**
     * 设置数据过期时间，单位毫秒
     * @param cacheRequestDTO
     * @return
     */
    Result setCacheDataExpireTime(CacheRequestDTO cacheRequestDTO);

    /**
     * 获取数据过期时间
     * @param cacheRequestDTO
     * @return
     */
    Result getCacheDataExpireTime(CacheRequestDTO cacheRequestDTO);

    /**
     * 获取string缓存数据
     * @param cacheRequestDTO
     * @return
     */
    Result getCacheData(CacheRequestDTO cacheRequestDTO);

    /**
     * 获取list缓存数据
     * @param cacheRequestDTO
     * @return
     */
    Result listCacheData(CacheRequestDTO cacheRequestDTO);

    /**
     * 查询数据是否存在
     * @param cacheRequestDTO
     * @return
     */
    Result exists(CacheRequestDTO cacheRequestDTO);
}
