package org.diwayou.cache.impl;

import com.google.common.collect.Maps;
import org.diwayou.cache.KvCache;

import java.util.concurrent.ConcurrentMap;

/**
 * @author gaopeng 2021/1/27
 */
public class MemoryKvCache implements KvCache {

    private ConcurrentMap<String, String> cache = Maps.newConcurrentMap();

    @Override
    public void set(String key, String value) {
        cache.put(key, value);
    }

    @Override
    public void set(String key, String value, long ttl) {
        set(key, value);
    }

    @Override
    public String get(String key) {
        return cache.get(key);
    }

    @Override
    public Boolean delete(String key) {
        cache.remove(key);

        return Boolean.TRUE;
    }
}
