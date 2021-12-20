package io.github.syske.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;

/**
 * @program: guava-demo
 * @description: cache
 * @author: syske
 * @date: 2021-05-01 16:40
 */
public class CacheBuilderDemo {

    public static void main(String[] args) throws ExecutionException {
        CacheBuilder cacheBuilder = CacheBuilder.newBuilder().maximumSize(10L).concurrencyLevel(5);
        Cache<String, String> cache = cacheBuilder.build();
        cache.put("Cache-test", "hello guava");
        for (int i = 0; i < 11; i++) {
            cache.put("cache" + i, "cache-value" + i);
        }
        System.out.println(cache.size());
        System.out.println(cache.getIfPresent("cache0"));
        System.out.println(cache.getIfPresent("cache10"));
        System.out.println(cache.getIfPresent("cache5"));
        System.out.println(cache.getIfPresent("Cache-test"));
        cache.invalidate("cache5");
        System.out.println(cache.getIfPresent("cache5"));
        String getCache = cache.get("cache0", () -> buildCache("cache0"));
        System.out.println(getCache);
    }

    private static String buildCache(String key) {
        return key + "value";
    }


}
