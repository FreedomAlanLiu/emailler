package org.daybreak.emailler.utils;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 2014/4/3.
 */
public class JedisPoolUtils {

    private static ShardedJedisPool pool;

    private static void createPool() {
        List<JedisShardInfo> shards = new ArrayList<>();

        JedisShardInfo si = new JedisShardInfo("218.244.142.197", 6379);
        shards.add(si);

        si = new JedisShardInfo("115.29.221.130", 6379);
        shards.add(si);

        si = new JedisShardInfo("112.124.66.214", 6379);
        shards.add(si);

        pool = new ShardedJedisPool(new JedisPoolConfig(), shards);
    }

    /**
     * 在多线程环境同步初始化
     */
    public static synchronized void initPool() {
        if (pool == null) {
            createPool();
        }
    }

    /**
     * 获取一个jedis 对象
     *
     * @return
     */
    public static ShardedJedis getJedis() {
        if (pool == null) {
            initPool();
        }
        return pool.getResource();
    }

    /**
     * 归还一个连接
     *
     * @param jedis
     */
    public static void returnRes(ShardedJedis jedis) {
        pool.returnResource(jedis);
    }
}
