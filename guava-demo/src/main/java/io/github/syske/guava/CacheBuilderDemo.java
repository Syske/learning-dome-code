package io.github.syske.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @program: guava-demo
 * @description: cache
 * @author: syske
 * @date: 2021-05-01 16:40
 */
public class CacheBuilderDemo {

    public static void main(String[] args) {
        CacheBuilder cacheBuilder = CacheBuilder.newBuilder();
        Cache cache = cacheBuilder.build();
//        cache.put("Cache-test", "hello guava");
        System.out.println(cache);
        System.out.println(cache.getIfPresent("Cache-test"));

    }


}
