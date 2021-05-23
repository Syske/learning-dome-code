package io.github.syske.springboot.activemq.demo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import com.sun.istack.internal.NotNull;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;

/**
 * @program: springboot-activemq-demo
 * @description: redis工具类
 * @author: syske
 * @date: 2021-05-23 9:49
 */
public class RedisUtil {
    // 数据源
    private ShardedJedisPool shardedJedisPool;

    /**
     * dbIndex和连接池的映射
     */
    private Map<Integer, ShardedJedisPool> shardedJedisPoolMap;

    /**
     * 它保证在执行操作之后释放数据源returnResource(jedis)
     *
     * @param <T>
     * @author fengjc
     * @version V1.0
     */
    abstract class Executor<T> {

        ShardedJedis jedis;
        ShardedJedisPool shardedJedisPool;

        public Executor(ShardedJedisPool shardedJedisPool) {
            this.shardedJedisPool = shardedJedisPool;
            jedis = this.shardedJedisPool.getResource();
        }

        /**
         * 回调
         *
         * @return 执行结果
         */
        abstract T execute();

        /**
         * 调用{@link #execute()}并返回执行结果 它保证在执行{@link #execute()}之后释放数据源returnResource(jedis)
         *
         * @return 执行结果
         */
        public T getResult() {
            T result = null;
            try {
                result = execute();
            } catch (Throwable e) {
                throw new RuntimeException("Redis execute exception", e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return result;
        }
    }

    /**
     * 删除模糊匹配的key
     *
     * @param likeKey
     *            模糊匹配的key
     * @return 删除成功的条数
     */
    public long delKeysLike(final String likeKey) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                Collection<Jedis> jedisC = jedis.getAllShards();
                Iterator<Jedis> iter = jedisC.iterator();
                long count = 0;
                while (iter.hasNext()) {
                    Jedis _jedis = iter.next();
                    Set<String> keys = _jedis.keys(likeKey + "*");
                    if (keys != null && keys.size() > 0)
                        count += _jedis.del(keys.toArray(new String[keys.size()]));
                }
                return count;
            }
        }.getResult();
    }

    /**
     * 删除
     *
     * @param key
     *            匹配的key
     * @return 删除成功的条数
     */
    public Long delKey(final String key) {
        return new Executor<Long>(shardedJedisPool) {
            @Override
            Long execute() {
                return jedis.del(key);
            }
        }.getResult();
    }

    /**
     * 删除
     *
     * @param keys
     *            匹配的key的集合
     * @return 删除成功的条数
     */
    public Long delKeys(final String[] keys) {
        return new Executor<Long>(shardedJedisPool) {
            @Override
            Long execute() {
                Collection<Jedis> jedisC = jedis.getAllShards();
                Iterator<Jedis> iter = jedisC.iterator();
                long count = 0;
                while (iter.hasNext()) {
                    Jedis _jedis = iter.next();
                    count += _jedis.del(keys);
                }
                return count;
            }
        }.getResult();
    }

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。 在 Redis 中，带有生存时间的 key 被称为『可挥发』(volatile)的。
     *
     * @param key
     *            key
     * @param expire
     *            生命周期，单位为秒
     * @return 1: 设置成功 0: 已经超时或key不存在
     */
    public Long expire(final String key, final int expire) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                return jedis.expire(key, expire);
            }
        }.getResult();
    }

    /**
     * 获取给定key的剩余有效时间
     *
     * @param key
     * @return
     */
    public Long getExpire(final String key) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                return jedis.ttl(key);
            }
        }.getResult();
    }

    /**
     * 一个跨jvm的id生成器，利用了redis原子性操作的特点
     *
     * @param key
     *            id的key
     * @return 返回生成的Id
     */
    public long makeId(final String key) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                long id = jedis.incr(key);
                if ((id + 75807) >= Long.MAX_VALUE) {
                    // 避免溢出，重置，getSet命令之前允许incr插队，75807就是预留的插队空间
                    jedis.getSet(key, "0");
                }
                return id;
            }
        }.getResult();
    }

    /* ======================================Strings====================================== */

    /**
     * 将字符串值 value 关联到 key 。 如果 key 已经持有其他值， setString 就覆写旧值，无视类型。 对于某个原本带有生存时间（TTL）的键来说， 当 setString 成功在这个键上执行时， 这个键原有的
     * TTL 将被清除。 时间复杂度：O(1)
     *
     * @param key
     *            key
     * @param value
     *            string value
     * @return 在设置操作成功完成时，才返回 OK 。
     */
    public String setString(final String key, final String value) {
        return new Executor<String>(shardedJedisPool) {

            @Override
            String execute() {
                return jedis.set(key, value);
            }
        }.getResult();
    }

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 expire (以秒为单位)。 如果 key 已经存在， 将覆写旧值。 类似于以下两个命令: SET key value EXPIRE key expire #
     * 设置生存时间 不同之处是这个方法是一个原子性(atomic)操作，关联值和设置生存时间两个动作会在同一时间内完成，在 Redis 用作缓存时，非常实用。 时间复杂度：O(1)
     *
     * @param key
     *            key
     * @param value
     *            string value
     * @param expire
     *            生命周期
     * @return 设置成功时返回 OK 。当 expire 参数不合法时，返回一个错误。
     */
    public String setString(final String key, final String value, final int expire) {
        return new Executor<String>(shardedJedisPool) {

            @Override
            String execute() {
                return jedis.setex(key, expire, value);
            }
        }.getResult();
    }

    /**
     * 时间复杂度： O(1) 为键 key 储存的数字值加上增量 increment 。 如果键 key 不存在， 那么键 key 的值会先被初始化为 0 ， 然后再执行 INCRBY 命令。 如果键 key
     * 储存的值不能被解释为数字， 那么 INCRBY 命令将返回一个错误。 本操作的值限制在 64 位(bit)有符号数字表示之内。
     *
     * @param key
     *            key
     * @param increment
     *            计数步长
     * @return 在加上增量 increment 之后， 键 key 当前的值。
     */
    public Long incrby(final String key, final long increment) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                return jedis.incrBy(key, increment);
            }
        }.getResult();
    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则 setStringIfNotExists 不做任何动作。 时间复杂度：O(1)
     *
     * @param key
     *            key
     * @param value
     *            string value
     * @return 设置成功，返回 1 。设置失败，返回 0 。
     */
    public Long setStringIfNotExists(final String key, final String value) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                return jedis.setnx(key, value);
            }
        }.getResult();
    }

    /**
     * 返回 key 所关联的字符串值。如果 key 不存在那么返回特殊值 nil 。 假如 key 储存的值不是字符串类型，返回一个错误，因为 getString 只能用于处理字符串值。 时间复杂度: O(1)
     *
     * @param key
     *            key
     * @return 当 key 不存在时，返回 nil ，否则，返回 key 的值。如果 key 不是字符串类型，那么返回一个错误。
     */
    public String getString(final String key) {
        return new Executor<String>(shardedJedisPool) {

            @Override
            String execute() {
                return jedis.get(key);
            }
        }.getResult();
    }

    /**
     * 批量的 {@link #setString(String, String)}
     *
     * @param pairs
     *            键值对数组{数组第一个元素为key，第二个元素为value}
     * @return 操作状态的集合
     */
    public List<Object> batchSetString(final List<Pair<String, String>> pairs) {
        return new Executor<List<Object>>(shardedJedisPool) {

            @Override
            List<Object> execute() {
                ShardedJedisPipeline pipeline = jedis.pipelined();
                for (Pair<String, String> pair : pairs) {
                    pipeline.set(pair.getKey(), pair.getValue());
                }
                return pipeline.syncAndReturnAll();
            }
        }.getResult();
    }

    /**
     * 批量的 {@link #getString(String)}
     *
     * @param keys
     *            key数组
     * @return value的集合
     */
    public List<String> batchGetString(final String[] keys) {
        return new Executor<List<String>>(shardedJedisPool) {

            @Override
            List<String> execute() {
                ShardedJedisPipeline pipeline = jedis.pipelined();
                List<String> result = new ArrayList<String>(keys.length);
                List<Response<String>> responses = new ArrayList<Response<String>>(keys.length);
                for (String key : keys) {
                    responses.add(pipeline.get(key));
                }
                pipeline.sync();
                for (Response<String> resp : responses) {
                    result.add(resp.get());
                }
                return result;
            }
        }.getResult();
    }

    /* ======================================Hashes====================================== */

    /**
     * 将哈希表 key 中的域 field 的值设为 value 。 如果 key 不存在，一个新的哈希表被创建并进行 hashSet 操作。 如果域 field 已经存在于哈希表中，旧值将被覆盖。 时间复杂度: O(1)
     *
     * @param key
     *            key
     * @param field
     *            域
     * @param value
     *            string value
     * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
     */
    public Long hashSet(final String key, final String field, final String value) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                return jedis.hset(key, field, value);
            }
        }.getResult();
    }

    public Set<String> hashKeys(final String key) {
        return new Executor<Set<String>>(shardedJedisPool) {
            @Override
            Set<String> execute() {
                return jedis.hkeys(key);
            }
        }.getResult();
    }

    /**
     * 将哈希表 key 中的域 field 的值设为 value 。 如果 key 不存在，一个新的哈希表被创建并进行 hashSet 操作。 如果域 field 已经存在于哈希表中，旧值将被覆盖。
     *
     * @param key
     *            key
     * @param field
     *            域
     * @param value
     *            string value
     * @param expire
     *            生命周期，单位为秒
     * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
     */
    public Long hashSet(final String key, final String field, final String value, final int expire) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                Pipeline pipeline = jedis.getShard(key).pipelined();
                Response<Long> result = pipeline.hset(key, field, value);
                pipeline.expire(key, expire);
                pipeline.sync();
                return result.get();
            }
        }.getResult();
    }

    /**
     * 返回哈希表 key 中给定域 field 的值。 时间复杂度:O(1)
     *
     * @param key
     *            key
     * @param field
     *            域
     * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。
     */
    public String hashGet(final String key, final String field) {
        return new Executor<String>(shardedJedisPool) {

            @Override
            String execute() {
                return jedis.hget(key, field);
            }
        }.getResult();
    }

    /**
     * 返回哈希表 key 中给定域 field 的值。 如果哈希表 key 存在，同时设置这个 key 的生存时间
     *
     * @param key
     *            key
     * @param field
     *            域
     * @param expire
     *            生命周期，单位为秒
     * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。
     */
    public String hashGet(final String key, final String field, final int expire) {
        return new Executor<String>(shardedJedisPool) {

            @Override
            String execute() {
                Pipeline pipeline = jedis.getShard(key).pipelined();
                Response<String> result = pipeline.hget(key, field);
                pipeline.expire(key, expire);
                pipeline.sync();
                return result.get();
            }
        }.getResult();
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。 时间复杂度: O(N) (N为fields的数量)
     *
     * @param key
     *            key
     * @param hash
     *            field-value的map
     * @return 如果命令执行成功，返回 OK 。当 key 不是哈希表(hash)类型时，返回一个错误。
     */
    public String hashMultipleSet(final String key, final Map<String, String> hash) {
        return new Executor<String>(shardedJedisPool) {

            @Override
            String execute() {
                return jedis.hmset(key, hash);
            }
        }.getResult();
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。同时设置这个 key 的生存时间
     *
     * @param key
     *            key
     * @param hash
     *            field-value的map
     * @param expire
     *            生命周期，单位为秒
     * @return 如果命令执行成功，返回 OK 。当 key 不是哈希表(hash)类型时，返回一个错误。
     */
    public String hashMultipleSet(final String key, final Map<String, String> hash, final int expire) {
        return new Executor<String>(shardedJedisPool) {

            @Override
            String execute() {
                Pipeline pipeline = jedis.getShard(key).pipelined();
                Response<String> result = pipeline.hmset(key, hash);
                pipeline.expire(key, expire);
                pipeline.sync();
                return result.get();
            }
        }.getResult();
    }

    /**
     * 返回哈希表 key 中，一个或多个给定域的值。如果给定的域不存在于哈希表，那么返回一个 nil 值。 时间复杂度: O(N) (N为fields的数量)
     *
     * @param key
     *            key
     * @param fields
     *            field的数组
     * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
     */
    public List<String> hashMultipleGet(final String key, final String... fields) {
        return new Executor<List<String>>(shardedJedisPool) {

            @Override
            List<String> execute() {
                return jedis.hmget(key, fields);
            }
        }.getResult();
    }

    /**
     * 返回哈希表 key 中，一个或多个给定域的值。如果给定的域不存在于哈希表，那么返回一个 nil 值。 同时设置这个 key 的生存时间
     *
     * @param key
     *            key
     * @param fields
     *            field的数组
     * @param expire
     *            生命周期，单位为秒
     * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
     */
    public List<String> hashMultipleGet(final String key, final int expire, final String... fields) {
        return new Executor<List<String>>(shardedJedisPool) {

            @Override
            List<String> execute() {
                Pipeline pipeline = jedis.getShard(key).pipelined();
                Response<List<String>> result = pipeline.hmget(key, fields);
                pipeline.expire(key, expire);
                pipeline.sync();
                return result.get();
            }
        }.getResult();
    }

    /**
     * 批量的{@link #hashMultipleSet(String, Map)}，在管道中执行
     *
     * @param pairs
     *            多个hash的多个field
     * @return 操作状态的集合
     */
    public List<Object> batchHashMultipleSet(final List<Pair<String, Map<String, String>>> pairs) {
        return new Executor<List<Object>>(shardedJedisPool) {

            @Override
            List<Object> execute() {
                ShardedJedisPipeline pipeline = jedis.pipelined();
                for (Pair<String, Map<String, String>> pair : pairs) {
                    pipeline.hmset(pair.getKey(), pair.getValue());
                }
                return pipeline.syncAndReturnAll();
            }
        }.getResult();
    }

    /**
     * 批量的{@link #hashMultipleSet(String, Map)}，在管道中执行
     *
     * @param data
     *            Map<String, Map<String, String>>格式的数据
     * @return 操作状态的集合
     */
    public List<Object> batchHashMultipleSet(final Map<String, Map<String, String>> data) {
        return new Executor<List<Object>>(shardedJedisPool) {

            @Override
            List<Object> execute() {
                ShardedJedisPipeline pipeline = jedis.pipelined();
                for (Map.Entry<String, Map<String, String>> iter : data.entrySet()) {
                    pipeline.hmset(iter.getKey(), iter.getValue());
                }
                return pipeline.syncAndReturnAll();
            }
        }.getResult();
    }

    /**
     * 批量的{@link #hashMultipleGet(String, String...)}，在管道中执行
     *
     * @param pairs
     *            多个hash的多个field
     * @return 执行结果的集合
     */
    public List<List<String>> batchHashMultipleGet(final List<Pair<String, String[]>> pairs) {
        return new Executor<List<List<String>>>(shardedJedisPool) {

            @Override
            List<List<String>> execute() {
                ShardedJedisPipeline pipeline = jedis.pipelined();
                List<List<String>> result = new ArrayList<List<String>>(pairs.size());
                List<Response<List<String>>> responses = new ArrayList<Response<List<String>>>(pairs.size());
                for (Pair<String, String[]> pair : pairs) {
                    responses.add(pipeline.hmget(pair.getKey(), pair.getValue()));
                }
                pipeline.sync();
                for (Response<List<String>> resp : responses) {
                    result.add(resp.get());
                }
                return result;
            }
        }.getResult();

    }

    /**
     * 返回哈希表 key 中，所有的域和值。在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。 时间复杂度: O(N)
     *
     * @param key
     *            key
     * @return 以列表形式返回哈希表的域和域的值。若 key 不存在，返回空列表。
     */
    public Map<String, String> hashGetAll(final String key) {
        return new Executor<Map<String, String>>(shardedJedisPool) {

            @Override
            Map<String, String> execute() {
                return jedis.hgetAll(key);
            }
        }.getResult();
    }

    /**
     * @Title: hashGetAll @Description: 指定库的hashgetall @param key @param dbIndex 库index @return
     *         java.util.Map<java.lang.String,java.lang.String> @throws
     */
    public Map<String, String> hashGetAllByIndex(final String key, final Integer dbIndex) {
        ShardedJedisPool jedisPool = null;
        if (null != shardedJedisPoolMap) {
            jedisPool = shardedJedisPoolMap.get(dbIndex);

        }
        if (null == jedisPool) {
            jedisPool = shardedJedisPool;
        }
        return new Executor<Map<String, String>>(jedisPool) {
            @Override
            Map<String, String> execute() {
                return jedis.hgetAll(key);
            }
        }.getResult();
    }

    /**
     * 返回哈希表 key 中，所有的域和值。在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。 同时设置这个 key 的生存时间
     *
     * @param key
     *            key
     * @param expire
     *            生命周期，单位为秒
     * @return 以列表形式返回哈希表的域和域的值。若 key 不存在，返回空列表。
     */
    public Map<String, String> hashGetAll(final String key, final int expire) {
        return new Executor<Map<String, String>>(shardedJedisPool) {

            @Override
            Map<String, String> execute() {
                Pipeline pipeline = jedis.getShard(key).pipelined();
                Response<Map<String, String>> result = pipeline.hgetAll(key);
                pipeline.expire(key, expire);
                pipeline.sync();
                return result.get();
            }
        }.getResult();
    }

    /**
     * 批量的{@link #hashGetAll(String)}
     *
     * @param keys
     *            key的数组
     * @return 执行结果的集合
     */
    public List<Map<String, String>> batchHashGetAll(final String... keys) {
        return new Executor<List<Map<String, String>>>(shardedJedisPool) {

            @Override
            List<Map<String, String>> execute() {
                ShardedJedisPipeline pipeline = jedis.pipelined();
                List<Map<String, String>> result = new ArrayList<Map<String, String>>(keys.length);
                List<Response<Map<String, String>>> responses =
                        new ArrayList<Response<Map<String, String>>>(keys.length);
                for (String key : keys) {
                    responses.add(pipeline.hgetAll(key));
                }
                pipeline.sync();
                for (Response<Map<String, String>> resp : responses) {
                    result.add(resp.get());
                }
                return result;
            }
        }.getResult();
    }

    /**
     * 批量的{@link #hashMultipleGet(String, String...)}，与{@link #batchHashGetAll(String...)}不同的是，返回值为Map类型
     *
     * @param keys
     *            key的数组
     * @return 多个hash的所有filed和value
     */
    public Map<String, Map<String, String>> batchHashGetAllForMap(final String... keys) {
        return new Executor<Map<String, Map<String, String>>>(shardedJedisPool) {

            @Override
            Map<String, Map<String, String>> execute() {
                ShardedJedisPipeline pipeline = jedis.pipelined();

                // 设置map容量防止rehash
                int capacity = 1;
                while ((int)(capacity * 0.75) <= keys.length) {
                    capacity <<= 1;
                }
                Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>(capacity);
                List<Response<Map<String, String>>> responses =
                        new ArrayList<Response<Map<String, String>>>(keys.length);
                for (String key : keys) {
                    responses.add(pipeline.hgetAll(key));
                }
                pipeline.sync();
                for (int i = 0; i < keys.length; ++i) {
                    result.put(keys[i], responses.get(i).get());
                }
                return result;
            }
        }.getResult();
    }

    /**
     * 删除掉hashkey 所对应的value
     *
     * @param key
     *            hash的key
     * @param field
     *            map 的field
     * @return
     */
    public long hashDel(final String key, final String... field) {

        return new Executor<Long>(shardedJedisPool) {
            @Override
            Long execute() {
                return jedis.hdel(key, field);
            }
        }.getResult();
    }

    /* ======================================List====================================== */

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     *
     * @param key
     *            key
     * @param values
     *            value的数组
     * @return 执行 listPushTail 操作后，表的长度
     */
    public Long listPushTail(final String key, final String... values) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                return jedis.rpush(key, values);
            }
        }.getResult();
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     *
     * @param key
     *            key
     * @param value
     *            string value
     * @return 执行 listPushHead 命令后，列表的长度。
     */
    public Long listPushHead(final String key, final String value) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                return jedis.lpush(key, value);
            }
        }.getResult();
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表头, 当列表大于指定长度是就对列表进行修剪(trim)
     *
     * @param key
     *            key
     * @param value
     *            string value
     * @param size
     *            链表超过这个长度就修剪元素
     * @return 执行 listPushHeadAndTrim 命令后，列表的长度。
     */
    public Long listPushHeadAndTrim(final String key, final String value, final long size) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                Pipeline pipeline = jedis.getShard(key).pipelined();
                Response<Long> result = pipeline.lpush(key, value);
                // 修剪列表元素, 如果 size - 1 比 end 下标还要大，Redis将 size 的值设置为 end 。
                pipeline.ltrim(key, 0, size - 1);
                pipeline.sync();
                return result.get();
            }
        }.getResult();
    }

    /**
     * 修剪列表
     */
    public Long listLRem(final String key, final String value) {
        return new Executor<Long>(shardedJedisPool) {
            @Override
            Long execute() {
                Long result = jedis.lrem(key, 1L, value);
                return result;
            }
        }.getResult();
    }

    public Long rpop(final String key) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                String result = jedis.rpop(key);
                if (Strings.isNotBlank(result)) {
                    return null;
                }
                return Long.valueOf(result);
            }
        }.getResult();
    }

    public String rpopStr(final String key) {
        return new Executor<String>(shardedJedisPool) {

            @Override
            String execute() {
                String result = jedis.rpop(key);
                if (Strings.isNotBlank(result)) {
                    return null;
                }
                return result;
            }
        }.getResult();
    }

    public String rpopForStr(final String key) {
        return new Executor<String>(shardedJedisPool) {

            @Override
            String execute() {
                return jedis.rpop(key);
            }
        }.getResult();
    }

    /**
     * 指定db上原子加一
     *
     * @param key
     * @param dbIndex
     * @return
     */
    public Long incrementAndGet(final String key, final Integer dbIndex) {
        ShardedJedisPool jedisPool = null;
        if (null != shardedJedisPoolMap) {
            jedisPool = shardedJedisPoolMap.get(dbIndex);

        }
        if (null == jedisPool) {
            jedisPool = shardedJedisPool;
        }
        return new Executor<Long>(jedisPool) {

            @Override
            Long execute() {

                return jedis.incr(key);
            }
        }.getResult();
    }

    /**
     * 返回list所有元素，下标从0开始，负值表示从后面计算，-1表示倒数第一个元素，key不存在返回空列表
     *
     * @param key
     *            key
     * @return list所有元素
     */
    public List<String> listGetAll(final String key) {
        return new Executor<List<String>>(shardedJedisPool) {

            @Override
            List<String> execute() {
                return jedis.lrange(key, 0, -1);
            }
        }.getResult();
    }

    /**
     * 返回指定区间内的元素，下标从0开始，负值表示从后面计算，-1表示倒数第一个元素，key不存在返回空列表
     *
     * @param key
     *            key
     * @param beginIndex
     *            下标开始索引（包含）
     * @param endIndex
     *            下标结束索引（不包含）
     * @return 指定区间内的元素
     */
    public List<String> listRange(final String key, final long beginIndex, final long endIndex) {
        return new Executor<List<String>>(shardedJedisPool) {

            @Override
            List<String> execute() {
                return jedis.lrange(key, beginIndex, endIndex - 1);
            }
        }.getResult();
    }

    /**
     * 一次获得多个链表的数据
     *
     * @param keys
     *            key的数组
     * @return 执行结果
     */
    public Map<String, List<String>> batchGetAllList(final List<String> keys) {
        return new Executor<Map<String, List<String>>>(shardedJedisPool) {

            @Override
            Map<String, List<String>> execute() {
                ShardedJedisPipeline pipeline = jedis.pipelined();
                Map<String, List<String>> result = new HashMap<String, List<String>>();
                List<Response<List<String>>> responses = new ArrayList<Response<List<String>>>(keys.size());
                for (String key : keys) {
                    responses.add(pipeline.lrange(key, 0, -1));
                }
                pipeline.sync();
                for (int i = 0; i < keys.size(); ++i) {
                    result.put(keys.get(i), responses.get(i).get());
                }
                return result;
            }
        }.getResult();
    }

    /* ======================================Pub/Sub====================================== */

    /**
     * 将信息 message 发送到指定的频道 channel。 时间复杂度：O(N+M)，其中 N 是频道 channel 的订阅者数量，而 M 则是使用模式订阅(subscribed patterns)的客户端的数量。
     *
     * @param channel
     *            频道
     * @param message
     *            信息
     * @return 接收到信息 message 的订阅者数量。
     */
    public Long publish(final String channel, final String message) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                Jedis _jedis = jedis.getShard(channel);
                return _jedis.publish(channel, message);
            }

        }.getResult();
    }

    /**
     * 订阅给定的一个频道的信息。
     *
     * @param jedisPubSub
     *            监听器
     * @param channel
     *            频道
     */
    public void subscribe(final JedisPubSub jedisPubSub, final String channel) {
        new Executor<Object>(shardedJedisPool) {

            @Override
            Object execute() {
                Jedis _jedis = jedis.getShard(channel);
                // 注意subscribe是一个阻塞操作，因为当前线程要轮询Redis的响应然后调用subscribe
                _jedis.subscribe(jedisPubSub, channel);
                return null;
            }
        }.getResult();
    }

    /**
     * 取消订阅
     *
     * @param jedisPubSub
     *            监听器
     */
    public void unSubscribe(final JedisPubSub jedisPubSub) {
        jedisPubSub.unsubscribe();
    }

    /* ======================================Sorted set================================= */

    /**
     * 将一个 member 元素及其 score 值加入到有序集 key 当中。
     *
     * @param key
     *            key
     * @param score
     *            score 值可以是整数值或双精度浮点数。
     * @param member
     *            有序集的成员
     * @param ttl
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
     */
    public Long addWithSortedSet(final String key, final double score, final String member, final Integer ttl) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                ShardedJedisPipeline pipelined = jedis.pipelined();
                Response<Long> response = pipelined.zadd(key, score, member);
                if (ttl != null) {
                    pipelined.expire(key, ttl);
                }
                pipelined.sync();
                return response.get();
            }
        }.getResult();
    }

    /**
     * @Title: batchAddWithSortedSet @Description: 批量zset @param key @param scoreMembers @param ttl @return
     *         java.lang.Long @throws
     */
    public Long batchAddWithSortedSet(final String key, final Map<String, Double> scoreMembers, final Integer ttl) {
        return new Executor<Long>(shardedJedisPool) {
            @Override
            Long execute() {
                ShardedJedisPipeline pipelined = jedis.pipelined();
                Response<Long> response = pipelined.zadd(key, scoreMembers);
                if (ttl != null) {
                    pipelined.expire(key, ttl);
                }
                pipelined.sync();
                return response.get();
            }
        }.getResult();
    }

    /**
     * 将多个 member 元素及其 score 值加入到有序集 key 当中。
     *
     * @param key
     *            key
     * @param scoreMembers
     *            score、member的pair
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
     */
    public Long addWithSortedSet(final String key, final Map<String, Double> scoreMembers) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                return jedis.zadd(key, scoreMembers);
            }
        }.getResult();
    }

    /**
     * 获取反向排行
     *
     * @param key
     * @param start
     * @param end
     * @param ttl
     * @return
     */
    public Set<Tuple> zRevRangeWithScore(final String key, final Integer start, final Integer end, final Integer ttl) {
        return new Executor<Set<Tuple>>(shardedJedisPool) {
            @Override
            Set<Tuple> execute() {
                ShardedJedisPipeline pipelined = jedis.pipelined();
                Response<Set<Tuple>> response = pipelined.zrevrangeWithScores(key, start, end);
                if (ttl != null) {
                    pipelined.expire(key, ttl);
                }
                pipelined.sync();
                return response.get();
            }
        }.getResult();
    }

    public Long zRevGetRank(final String key, final String member, Integer ttl) {
        return new Executor<Long>(shardedJedisPool) {
            @Override
            Long execute() {
                ShardedJedisPipeline pipelined = jedis.pipelined();
                Response<Long> response = pipelined.zrevrank(key, member);
                if (ttl != null) {
                    pipelined.expire(key, ttl);
                }
                pipelined.sync();
                return response.get();
            }
        }.getResult();
    }

    /**
     * 将多个 value 元素加入到有序集 key 当中。
     */
    public void addSet(final String key, final Set<Long> values) {
        new Executor<List<Object>>(shardedJedisPool) {

            @Override
            List<Object> execute() {
                ShardedJedisPipeline pipeline = jedis.pipelined();
                for (Long value : values) {
                    pipeline.sadd(key, String.valueOf(value));
                }
                return pipeline.syncAndReturnAll();
            }
        }.getResult();
    }

    /**
     * 判断 Redis中是否存在Key
     * @param key 待判断的key
     * @return boolean
     */
    public Boolean existKey(String key) {
        return new Executor<Boolean>(shardedJedisPool) {
            @Override
            Boolean execute() {
                return jedis.exists(key);
            }
        }.getResult();
    }

    /**
     * 向set中添加一个或多个元素
     * @param key key值
     * @param expire 过期时间，单位秒
     * @param values 待添加的元素
     */
    public Long sAdd(String key, int expire, String... values) {
        return new Executor<Long>(shardedJedisPool) {
            @Override
            Long execute() {
                Long count = jedis.sadd(key, values);
                if (expire > 0) {
                    jedis.expire(key, expire);
                }
                return count;
            }
        }.getResult();
    }

    /**
     * 移除set中的一个或多个元素
     * @param key key值
     * @param values 待移除的元素
     */
    public Long sRemove(String key, String... values) {
        return new Executor<Long>(shardedJedisPool) {
            @Override
            Long execute() {
                return jedis.srem(key, values);
            }
        }.getResult();
    }


    /**
     * 获取set
     */
    public Set<String> getSet(final String key) {
        return new Executor<Set<String>>(shardedJedisPool) {

            @Override
            Set<String> execute() {
                ShardedJedisPipeline pipeline = jedis.pipelined();
                Response<Set<String>> smembers = pipeline.smembers(key);
                pipeline.sync();
                return smembers.get();
            }
        }.getResult();
    }

    /**
     * 判断set中是否存在某元素
     * @param key key值
     * @param member 待判断的元素, 不可为 null
     * @return Boolean
     */
    public Boolean existMemberForSet(String key, @NotNull String member) {
        return new Executor<Boolean>(shardedJedisPool) {

            @Override
            Boolean execute() {
                return jedis.sismember(key, member);
            }
        }.getResult();
    }



    /**
     * 批量移除sorted set元素
     *
     * @param key
     * @param members
     * @return
     */
    public Long zRemMemberFromSortedSet(String key, List<String> members) {
        if (CollectionUtils.isEmpty(members)) {
            return null;
        }
        String[] member = members.toArray(new String[members.size()]);
        return new Executor<Long>(shardedJedisPool) {
            @Override
            Long execute() {
                ShardedJedisPipeline pipelined = jedis.pipelined();
                Response<Long> response = pipelined.zrem(key, member);
                pipelined.syncAndReturnAll();
                return response.get();
            }
        }.getResult();
    }

    /**
     * 获取成员分数
     *
     * @param key
     * @param member
     * @param ttl
     * @return
     */
    public Double zScore(final String key, final String member, Integer ttl) {
        return new Executor<Double>(shardedJedisPool) {
            @Override
            Double execute() {
                ShardedJedisPipeline pipelined = jedis.pipelined();
                Response<Double> response = pipelined.zscore(key, member);
                if (ttl != null) {
                    pipelined.expire(key, ttl);
                }
                pipelined.sync();
                return response.get();
            }
        }.getResult();
    }

    /**
     * 获取总量
     *
     * @param key
     * @param ttl
     * @return
     */
    public Long getScoreTotal(final String key, final Integer ttl) {
        return new Executor<Long>(shardedJedisPool) {
            @Override
            Long execute() {
                ShardedJedisPipeline pipeline = jedis.pipelined();
                // 查找时间窗口中的行为数量
                Response<Long> count = pipeline.zcard(key);
                if (ttl != null) {
                    pipeline.expire(key, ttl);
                }
                pipeline.sync();
                return count.get();
            }
        }.getResult();
    }

    /**
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。 有序集成员按 score 值递减(从大到小)的次序排列。
     *
     * @param key
     *            key
     * @param max
     *            score最大值
     * @param min
     *            score最小值
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    public Set<String> revrangeByScoreWithSortedSet(final String key, final double max, final double min) {
        return new Executor<Set<String>>(shardedJedisPool) {

            @Override
            Set<String> execute() {
                return jedis.zrevrangeByScore(key, max, min);
            }
        }.getResult();
    }

    public Set<String> test() {
        return new Executor<Set<String>>(shardedJedisPool) {

            @Override
            Set<String> execute() {
                jedis.lrange("key", 10, 20);
                // return jedis.zrevrangeByScore(key, max, min);
                return null;
            }
        }.getResult();
    }

    /**
     * 设置byte类型的，需要自己序列化
     *
     * @param key
     * @param value
     * @return
     */
    public Long setByte(final byte[] key, final byte[] value, final int timeout) {
        return new Executor<Long>(shardedJedisPool) {
            @Override
            Long execute() {
                jedis.set(key, value);
                return jedis.expire(key, timeout);

            }
        }.getResult();

    }

    /**
     * 根据key读取byte类型，需要自己反序列化
     *
     * @param key
     * @return
     */
    public byte[] getByte(final byte[] key) {

        return new Executor<byte[]>(shardedJedisPool) {
            @Override
            byte[] execute() {
                return jedis.get(key);
            }
        }.getResult();
    }

    /**
     * 查询操作是否被限流
     *
     * @param actionKey
     *            操作key
     * @param period
     *            时间范围（单位秒）
     * @param maxCount
     *            最大次数
     * @return 操作是否允许
     */
    public Boolean isActionAllowed(String actionKey, int period, int maxCount) {
        return new Executor<Boolean>(shardedJedisPool) {
            @Override
            Boolean execute() {
                ShardedJedisPipeline pipeline = jedis.pipelined();
                // value中存放纳秒级的时间戳
                long nowTs = System.nanoTime();
                // 记录本次操作行为
                pipeline.zadd(actionKey, nowTs, "" + nowTs);
                // 移除时间窗口外的行为
                pipeline.zremrangeByScore(actionKey, 0, nowTs - period * 1000 * 1000 * 1000);
                // 查找时间窗口中的行为数量
                Response<Long> count = pipeline.zcard(actionKey);
                // 重置时间窗口
                pipeline.expire(actionKey, period + 1);
                pipeline.sync();
                return count.get() <= maxCount;
            }
        }.getResult();
    }

    /* ======================================Other====================================== */

    /**
     * 设置数据源
     *
     * @param shardedJedisPool
     *            数据源
     */
    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public void setShardedJedisPoolMap(Map<Integer, ShardedJedisPool> shardedJedisPoolMap) {
        this.shardedJedisPoolMap = shardedJedisPoolMap;
    }

    /**
     * 构造Pair键值对
     *
     * @param key
     *            key
     * @param value
     *            value
     * @return 键值对
     */
    public <K, V> Pair<K, V> makePair(K key, V value) {
        return new Pair<K, V>(key, value);
    }

    /**
     * 键值对
     *
     * @param <K>
     *            key
     * @param <V>
     *            value
     * @author fengjc
     * @version V1.0
     */
    public class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    /**
     * 指定db getString
     *
     * @param key
     * @param dbIndex
     * @return
     */
    public String getString(final String key, final Integer dbIndex) {
        ShardedJedisPool jedisPool = null;
        if (null != shardedJedisPoolMap) {
            jedisPool = shardedJedisPoolMap.get(dbIndex);

        }
        if (null == jedisPool) {
            jedisPool = shardedJedisPool;
        }
        return new Executor<String>(jedisPool) {

            @Override
            String execute() {
                return jedis.get(key);
            }
        }.getResult();
    }
}
