package io.github.syske.cacheserver;

import io.github.syske.cacheserver.util.CacheUtil;
import org.junit.jupiter.api.Test;

/**
 * @program: cache-server
 * @description: 缓存测试
 * @author: syske
 * @create: 2021-02-24 17:04
 */
public class CacheTest {
    private CacheUtil cacheUtil = new CacheUtil();

    @Test
    public void cacheData() throws Exception {
        boolean singleObjectData = cacheUtil.cacheSingleObjectData("这里是测试内容", "test-cache-12312", -1L);
        System.out.println(singleObjectData);
    }

    @Test
    public void deleteData() throws Exception {
        boolean singleObjectData = cacheUtil.deleteCacheData("hospitalInfoList");
        System.out.println(singleObjectData);
    }
}
