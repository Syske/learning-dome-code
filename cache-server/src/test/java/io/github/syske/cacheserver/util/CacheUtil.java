package io.github.syske.cacheserver.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.github.syske.cacheserver.dto.CacheRequestDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * @program: cache-server
 * @description: 缓存工具类
 * @author: syske
 * @create: 2021-02-24 13:59
 */
public class CacheUtil<T> {
    private final HttpClientUtil httpClientUtil = new HttpClientUtil();
    private final Log log = LogFactory.getLog(CacheUtil.class);

    /**
     * 缓存服务地址
     */
    private final String CACHE_SERVER_URL_PRE = PropertiesUtil.getInstance("config").get("cache.server.url");

    /**
     * 缓存单个数据
     * @param obj 要缓存的数据
     * @param key 缓存数据的key，必须唯一，否则会覆盖已有缓存数据
     * @param expireTime 缓存过期时间，如果永不过期，传NULL
     * @return
     */
    public boolean cacheSingleObjectData(String key,T obj,  Long expireTime) throws Exception {
        String serverUrl = CACHE_SERVER_URL_PRE + "/cacheSingleObjectData/";
        CacheRequestDTO<T> cacheRequestDTO = new CacheRequestDTO();
        cacheRequestDTO.setData(obj);
        cacheRequestDTO.setKey(key);
        cacheRequestDTO.setExpireTime(expireTime);
        log.debug("请求地址：" + serverUrl);
        String result = httpClientUtil.doPost(serverUrl, JSON.toJSONString(cacheRequestDTO));
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject.getBoolean("success");
    }

    /**
     * 缓存list数据
     * @param list
     * @return
     */
    public boolean cacheListData(String key, List<T> list, Long expireTime) throws Exception {
        String serverUrl = CACHE_SERVER_URL_PRE + "/cacheListData/";
        CacheRequestDTO<List<T>> cacheRequestDTO = new CacheRequestDTO();
        cacheRequestDTO.setData(list);
        cacheRequestDTO.setKey(key);
        cacheRequestDTO.setExpireTime(expireTime);
        log.debug("请求地址：" + serverUrl);
        String result = httpClientUtil.doPost(serverUrl, JSON.toJSONString(cacheRequestDTO));
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject.getBoolean("success");
    }

    /**
     * 删除缓存数据
     * @param key 要删除的缓存数据的key
     * @return
     */
    public boolean deleteCacheData(String key) throws Exception {
        String serverUrl = CACHE_SERVER_URL_PRE + "/deleteCacheData/";
        CacheRequestDTO cacheRequestDTO = new CacheRequestDTO();
        cacheRequestDTO.setKey(key);
        log.debug("请求地址：" + serverUrl);
        String result = httpClientUtil.doPost(serverUrl, JSON.toJSONString(cacheRequestDTO));
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject.getBoolean("success");
    }

    /**
     * 设置缓存数据过期时间
     * @param key 要设置过期时间的缓存数据的key
     * @return
     */
    public boolean setCacheDataExpireTime(String key, Long expireTime) throws Exception {
        String serverUrl = CACHE_SERVER_URL_PRE + "/setCacheDataExpireTime/";
        CacheRequestDTO cacheRequestDTO = new CacheRequestDTO();
        cacheRequestDTO.setKey(key);
        cacheRequestDTO.setExpireTime(expireTime);
        log.debug("请求地址：" + serverUrl);
        String result = httpClientUtil.doPost(serverUrl, JSON.toJSONString(cacheRequestDTO));
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject.getBoolean("success");
    }

    /**
     * 获取缓存数据过期时间，单位ms
     * @param key 缓存数据的key
     * @return
     */
    public long getCacheDataExpireTime(String key) throws Exception {
        String serverUrl = CACHE_SERVER_URL_PRE + "/getCacheDataExpireTime/";
        CacheRequestDTO cacheRequestDTO = new CacheRequestDTO();
        cacheRequestDTO.setKey(key);
        log.debug("请求地址：" + serverUrl);
        String result = httpClientUtil.doPost(serverUrl, JSON.toJSONString(cacheRequestDTO));
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject.getLong("result");
    }

    /**
     * 获取单个缓存数据
     * @param key 缓存数据的key
     * @return
     */
    public T getCacheData(String key, Class<T> tClass) throws Exception {
        String serverUrl = CACHE_SERVER_URL_PRE + "/getCacheData/";
        CacheRequestDTO cacheRequestDTO = new CacheRequestDTO();
        cacheRequestDTO.setKey(key);
        log.debug("请求地址：" + serverUrl);
        String result = httpClientUtil.doPost(serverUrl, JSON.toJSONString(cacheRequestDTO));
        JSONObject jsonObject = JSON.parseObject(result);
        return JSON.parseObject(jsonObject.getString("result"), tClass);
    }

    /**
     * 获取单个缓存数据
     * @param key 缓存数据的key
     * @return
     */
    public List<T> listCacheData(String key) throws Exception {
        String serverUrl = CACHE_SERVER_URL_PRE + "/listCacheData/";
        CacheRequestDTO cacheRequestDTO = new CacheRequestDTO();
        cacheRequestDTO.setKey(key);
        log.debug("请求地址：" + serverUrl);
        String result = httpClientUtil.doPost(serverUrl, JSON.toJSONString(cacheRequestDTO));
        JSONObject jsonObject = JSON.parseObject(result);
        return JSON.parseObject(jsonObject.getString("result"), new TypeReference<List<T>>(){});
    }

    /**
     * 核查数据是否已经缓存
     * @param key 缓存数据的key
     * @return
     */
    public boolean exists(String key) throws Exception {
        String serverUrl = CACHE_SERVER_URL_PRE + "/exists/";
        CacheRequestDTO cacheRequestDTO = new CacheRequestDTO();
        cacheRequestDTO.setKey(key);
        log.debug("请求地址：" + serverUrl);
        String result = httpClientUtil.doPost(serverUrl, JSON.toJSONString(cacheRequestDTO));
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject.getBoolean("result");
    }

}
