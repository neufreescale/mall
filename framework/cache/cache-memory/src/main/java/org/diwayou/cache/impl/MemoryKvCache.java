package org.diwayou.cache.impl;

import com.google.common.collect.Maps;
import org.diwayou.cache.KvCache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author gaopeng 2021/1/27
 */
public class MemoryKvCache implements KvCache {

    private ConcurrentMap<String, CacheData> cache = Maps.newConcurrentMap();

    @Override
    public void set(String key, String value) {
        cache.put(key, CacheData.create(value));
    }

    @Override
    public void set(String key, String value, long ttl) {
        long millis = TimeUnit.SECONDS.toMillis(ttl) + System.currentTimeMillis();

        cache.put(key, CacheData.create(value, millis));
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
    public Boolean setIfAbsent(String key, String value, int timeout, TimeUnit timeUnit) {
        return Boolean.TRUE;
    }
}
