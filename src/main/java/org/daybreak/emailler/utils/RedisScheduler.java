package org.daybreak.emailler.utils;

import redis.clients.jedis.ShardedJedis;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * Created by Alan on 2014/4/3.
 */
public class RedisScheduler implements Scheduler {

    private static final String QUEUE_PREFIX = "queue_";

    private static final String SET_PREFIX = "set_";

    public RedisScheduler() {
        JedisPoolUtils.initPool();
    }

    @Override
    public void push(Request request, Task task) {
        ShardedJedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            //使用SortedSet进行url去重
            if (jedis.zrank(SET_PREFIX + task.getUUID(), request.getUrl()) == null) {
                //使用List保存队列
                jedis.rpush(QUEUE_PREFIX + task.getUUID(), request.getUrl());
                jedis.zadd(SET_PREFIX + task.getUUID(), System.currentTimeMillis(), request.getUrl());
            }
        } finally {
            if (jedis != null) {
                JedisPoolUtils.returnRes(jedis);
            }
        }
    }

    @Override
    public Request poll(Task task) {
        ShardedJedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            String url = jedis.lpop(QUEUE_PREFIX + task.getUUID());
            if (url == null) {
                return null;
            }
            return new Request(url);
        } finally {
            if (jedis != null) {
                JedisPoolUtils.returnRes(jedis);
            }
        }
    }
}
