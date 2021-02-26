package org.diwayou.cache.impl;

import com.google.common.collect.Maps;
import org.diwayou.cache.KvCache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author gaopeng 2021/1/27
 */
public class MemoryKvCache implements KvCache {

    private final ConcurrentMap<String, CacheData> cache = Maps.newConcurrentMap();

    @Override
    public void set(String key, String value) {
        cache.put(key, CacheData.create(value));
    }

    @Override
    public void set(String key, String value, long ttl, TimeUnit unit) {
        cache.put(key, CacheData.create(value, toRealTtl(ttl, unit)));
    }

    @Override
    public String get(String key) {
        CacheData data = cache.get(key);
        if (data == null) {
            return null;
        }

        if (data.getTtl() < System.currentTimeMillis()) {
            cache.remove(key, data);

            return null;
        }

        return data.getValue();
    }

    @Override
    public Boolean delete(String key) {
        cache.remove(key);

        return Boolean.TRUE;
    }

    @Override
    public Boolean setIfAbsent(String key, String value, long timeout, TimeUnit unit) {
        CacheData data = CacheData.create(value, toRealTtl(timeout, unit));
        CacheData old = cache.putIfAbsent(key, data);
        if (old != null) {
            // 检验是否过期
            if (get(key) == null) {
                // 过期重新put
                old = cache.putIfAbsent(key, data);
                // 如果并发，忽略直接返回false
                if (old != null) {
                    return Boolean.FALSE;
                }
            }
        }

        return Boolean.TRUE;
    }

    private static long toRealTtl(long ttl, TimeUnit unit) {
        return unit.toMillis(ttl) + System.currentTimeMillis();
    }
}
