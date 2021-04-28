package io.github.syske.cacheserver.controller;

import io.github.syske.cacheserver.dto.CacheRequestDTO;
import io.github.syske.cacheserver.dto.Result;
import io.github.syske.cacheserver.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: cache-server
 * @description: 缓存服务controller
 * @author: syske
 * @create: 2021-02-23 11:38
 */
@RestController("cacheController")
@RequestMapping("cache/")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    /**
     * 缓存String数据
     *
     * @param cacheRequestDTO 缓存数据
     * @return
     */
    @RequestMapping("cacheSingleObjectData/")
    public Result<String> cacheSingleObjectData(@RequestBody CacheRequestDTO<String> cacheRequestDTO) {
        return cacheService.cacheSingleObjectData(cacheRequestDTO);
    }

    /**
     * 缓存List数据
     *
     * @param cacheRequestDTO 缓存数据
     * @return
     */
    @RequestMapping("cacheListData/")
    public Result<List> cacheListData(@RequestBody CacheRequestDTO<List> cacheRequestDTO) {
        return cacheService.cacheListData(cacheRequestDTO);
    }

    /**
     * 删除缓存数据
     *
     * @param cacheRequestDTO 缓存数据
     * @return
     */
    @RequestMapping("deleteCacheData/")
    public Result deleteCacheData(@RequestBody CacheRequestDTO cacheRequestDTO) {
        return cacheService.deleteCacheData(cacheRequestDTO);
    }

    /**
     * 设置缓存数据过期时间
     *
     * @param cacheRequestDTO 缓存数据
     * @return
     */
    @RequestMapping("setCacheDataExpireTime/")
    public Result setCacheDataExpireTime(@RequestBody CacheRequestDTO cacheRequestDTO) {
        return cacheService.setCacheDataExpireTime(cacheRequestDTO);
    }

    /**
     * 获取缓存数据过期时间
     *
     * @param cacheRequestDTO 缓存数据
     * @return
     */
    @RequestMapping("getCacheDataExpireTime/")
    public Result getCacheDataExpireTime(@RequestBody CacheRequestDTO cacheRequestDTO) {
        return cacheService.getCacheDataExpireTime(cacheRequestDTO);
    }

    /**
     * 获取单个缓存数据
     *
     * @param cacheRequestDTO 缓存数据
     * @return
     */
    @RequestMapping("getCacheData/")
    public Result getCacheData(@RequestBody CacheRequestDTO cacheRequestDTO) {
        return cacheService.getCacheData(cacheRequestDTO);
    }

    /**
     * 获取缓存数据list
     *
     * @param cacheRequestDTO 缓存数据
     * @return
     */
    @RequestMapping("listCacheData/")
    public Result listCacheData(@RequestBody CacheRequestDTO cacheRequestDTO) {
        return cacheService.listCacheData(cacheRequestDTO);
    }

    /**
     * 核查数据是否已经缓存
     *
     * @param cacheRequestDTO 缓存数据
     * @return
     */
    @RequestMapping("exists/")
    public Result exists(@RequestBody CacheRequestDTO cacheRequestDTO) {
        return cacheService.exists(cacheRequestDTO);
    }
}
