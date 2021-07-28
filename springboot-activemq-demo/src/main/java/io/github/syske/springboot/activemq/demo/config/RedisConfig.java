package io.github.syske.springboot.activemq.demo.config;

import com.google.common.collect.Maps;
import io.github.syske.springboot.activemq.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: springboot-activemq-demo
 * @description: redis配置
 * @author: syske
 * @date: 2021-05-23 11:32
 */
@Configuration
public class RedisConfig {
    @Value("${redis.host.uri}")
    private String REDIS_HOST_URI;
    @Bean
    public RedisUtil redisUtil() {

        RedisUtil redisUtil = new RedisUtil();

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1024);
        jedisPoolConfig.setMaxIdle(200);
        jedisPoolConfig.setMaxWaitMillis(1000);
        jedisPoolConfig.setTestOnBorrow(false);

        List<JedisShardInfo> shards = new ArrayList<>();
        JedisShardInfo jedisShardInfo = new JedisShardInfo(REDIS_HOST_URI);
        shards.add(jedisShardInfo);
        redisUtil.setShardedJedisPool(new ShardedJedisPool(jedisPoolConfig, shards));
        return redisUtil;
    }
}
