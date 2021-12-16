package io.github.syske.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * @program: caffeine-demo
 * @description: caffeine缓存测试
 * @author: syske
 * @date: 2021-12-15 22:29
 */
public class CaffeineDemo {
    public static void main(String[] args) {

        Cache<String, Object> cache =
            Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(10_000).build();
        String key = "hello_caffeine";

        // 查找一个缓存元素， 没有查找到的时候返回null
        Object object = cache.getIfPresent(key);
        // 查找缓存，如果缓存不存在则生成缓存元素, 如果无法生成则返回null
        object = cache.get(key, k -> createObject(key));
        // 添加或者更新一个缓存元素
        cache.put(key, object);
        // 移除一个缓存元素
        cache.invalidate(key);
        System.out.println(cache);

        // 自动加载
        LoadingCache<String, Object> cache2 = Caffeine.newBuilder().maximumSize(10_000)
            .expireAfterWrite(10, TimeUnit.MINUTES).build(CaffeineDemo::createObject);

        String key2 = "hello2";
        // 查找缓存，如果缓存不存在则生成缓存元素, 如果无法生成则返回null
        Object value = cache2.get(key2);
        System.out.println(value);
        List<String> keys = new ArrayList<>();
        keys.add("hello1");
        keys.add("hello2");
        // 批量查找缓存，如果缓存不存在则生成缓存元素
        Map<String, Object> objectMap = cache2.getAll(keys);
        System.out.println(objectMap);
    }

    private static Object createObject(String key) {
        return "hello caffeine 2021";
    }
}