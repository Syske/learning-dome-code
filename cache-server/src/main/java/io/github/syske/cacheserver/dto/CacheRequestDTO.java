package io.github.syske.cacheserver.dto;

import java.io.Serializable;

/**
 * @program: cache-server
 * @description: 缓存服务入参
 * @author: syske
 * @create: 2021-02-24 11:28
 */
public class CacheRequestDTO<T> implements Serializable {
    /**
     * 缓存key
     */
    private String key;
    /**
     * 缓存数据
     */
    private T data;
    /**
     * 缓存过期时间 毫秒
     */
    private Long expireTime;
    /**
     * list起始索引
     */
    private Long listStartIndex;
    /**
     * list终止索引
     */
    private Long listEndIndex;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Long getListStartIndex() {
        return listStartIndex;
    }

    public void setListStartIndex(Long listStartIndex) {
        this.listStartIndex = listStartIndex;
    }

    public Long getListEndIndex() {
        return listEndIndex;
    }

    public void setListEndIndex(Long listEndIndex) {
        this.listEndIndex = listEndIndex;
    }

    @Override
    public String toString() {
        return "CacheRequestDTO{" +
                "key='" + key + '\'' +
                ", data=" + data +
                ", expireTime=" + expireTime +
                ", listStartIndex=" + listStartIndex +
                ", listEndIndex=" + listEndIndex +
                '}';
    }
}
