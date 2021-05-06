package io.github.syske.redisson;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.*;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @program: redisson-demo
 * @description: redisson配置
 * @author: syske
 * @create: 2021-03-18 11:10
 */
public class RedissonConfig {

    private Config getConfig () {
        // 1. Create config object
        Config config = new Config();
        config.useClusterServers()
                // use "rediss://" for SSL connection
                .addNodeAddress("redis://127.0.0.1:7001","redis://127.0.0.1:7002",
                        "redis://127.0.0.1:7003","redis://127.0.0.1:7004",
                        "redis://127.0.0.1:7005","redis://127.0.0.1:7006").setPassword("pansky-redis").setTimeout(5000);
        return config;
    }

    private RedissonClient getRedissonClient(Config config) {
        // 2. Create Redisson instance

// Sync and Async API
//        RedissonClient redisson = Redisson.create(config);

// Reactive API
//        RedissonReactiveClient redissonReactive = Redisson.createReactive(config);

// RxJava2 API
//        RedissonRxClient redissonRx = Redisson.createRx(config);
        return Redisson.create(config);
    }

    private void getRmap(RedissonClient redisson) {
        // 3. Get Redis based implementation of java.util.concurrent.ConcurrentMap
        RMap<String, String> map = redisson.getMap("myMap");

//        RMapReactive<String, String> mapReactive = redissonReactive.getMap("myMap");

//        RMapRx<String, String> mapRx = redissonRx.getMap("myMap");
    }

    private RLock getLock(RedissonClient redissonClient) {
        // 4. Get Redis based implementation of java.util.concurrent.locks.Lock
        RLock lock = redissonClient.getLock("myLock");

//        RLockReactive lockReactive = redissonClient.getLock("myLock");

//        RLockRx lockRx = redissonRx.getLock("myLock");
        return lock;
    }


    public static void main(String[] args) throws InterruptedException {
        RedissonConfig redissonConfig = new RedissonConfig();
        Config config = redissonConfig.getConfig();
        RedissonClient redissonClient = redissonConfig.getRedissonClient(config);
        RLock lock1 = redissonConfig.getLock(redissonClient);
        RLock lock2 = redissonConfig.getLock(redissonClient);
        RLock lock3 = redissonConfig.getLock(redissonClient);
        RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);
        boolean isLock;
        try {
            isLock = redLock.tryLock(500, 30000, TimeUnit.MILLISECONDS);
            System.out.println("isLock = "+isLock);
            if (isLock) {
                //TODO if get lock success, do something;
                Thread.sleep(30000);
            }
        } catch (Exception e) {
        } finally {
            // 无论如何, 最后都要解锁
            System.out.println("");
            redLock.unlock();
        }
    }

}
