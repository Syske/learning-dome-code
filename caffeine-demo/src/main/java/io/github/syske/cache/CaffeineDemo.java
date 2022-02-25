package io.github.syske.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.*;

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

        // 手动异步加载
        AsyncCache<String, Object> cache3 = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(10_000)
                .buildAsync();

        // 查找一个缓存元素， 没有查找到的时候返回null
        CompletableFuture<Object> graph = cache3.getIfPresent(key);
        // 查找缓存元素，如果不存在，则异步生成
        graph = cache3.get(key, k -> createObject(key));
        // 添加或者更新一个缓存元素
        cache3.put(key, graph);
        // 移除一个缓存元素
        cache3.synchronous().invalidate(key);

        // 自动异步加载
        AsyncLoadingCache<String, Object> cache4 = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // 你可以选择: 去异步的封装一段同步操作来生成缓存元素
                .buildAsync(key3 -> createObject(key3));
        // 你也可以选择: 构建一个异步缓存元素操作并返回一个future
//    .buildAsync((key3, executor) -> createObject(key3, executor));

// 查找缓存元素，如果其不存在，将会异步进行生成
        CompletableFuture<Object> object4 = cache4.get(key);
// 批量查找缓存元素，如果其不存在，将会异步进行生成
        CompletableFuture<Map<String, Object>> graphs = cache4.getAll(keys);
    }

  /*  private static  CompletableFuture<Object> createObject(String key, Executor executor) {
        executor.execute(() -> {

        });
    }*/

    private static Object createObject(String key) {
        return "hello caffeine 2021";
    }
}