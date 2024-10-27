package io.github.syske.redisson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.UUID;

public class DistributedLock {
    private Jedis jedis;
    private String lockKey;
    private int expireTime; // 锁的过期时间（秒）

    public DistributedLock(Jedis jedis, String lockKey, int expireTime) {
        this.jedis = jedis;
        this.lockKey = lockKey;
        this.expireTime = expireTime;
    }

    /**
     * 尝试获取锁
     * @return 如果成功获取锁则返回锁标识符，否则返回null
     */
    public String tryLock() {
        String identifier = UUID.randomUUID().toString();
        // 使用setnx命令尝试设置锁，并设置过期时间
        SetParams setParams = SetParams.setParams().nx().ex(expireTime);
        String result = jedis.set(lockKey, identifier, setParams);
        if ("OK".equals(result)) {
            return identifier;
        }
        return null;
    }

    /**
     * 释放锁
     * @param identifier 当前持有的锁标识符
     * @return 如果成功释放锁则返回true，否则返回false
     */
    public boolean releaseLock(String identifier) {
        // 使用Lua脚本来保证删除操作的原子性
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, 1, lockKey, identifier);
        return "1".equals(result.toString());
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        DistributedLock lock = new DistributedLock(jedis, "myDistributedLock", 60);

        String identifier = lock.tryLock();
        if (identifier != null) {
            System.out.println("Lock acquired with identifier: " + identifier);
            try {
                // 执行业务逻辑
                Thread.sleep(5000); // 模拟耗时操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock.releaseLock(identifier)) {
                    System.out.println("Lock released successfully.");
                } else {
                    System.err.println("Failed to release lock.");
                }
            }
        } else {
            System.err.println("Failed to acquire lock.");
        }

        jedis.close();
    }
}