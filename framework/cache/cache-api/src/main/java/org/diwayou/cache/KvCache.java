package org.diwayou.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author gaopeng 2021/1/27
 */
public interface KvCache {

    void set(String key, String value);

    default void set(String key, String value, long seconds) {
        set(key, value, seconds, TimeUnit.SECONDS);
    }

    void set(String key, String value, long ttl, TimeUnit unit);

    String get(String key);

    Boolean delete(String key);

    Boolean setIfAbsent(String key, String value, long timeout, TimeUnit unit);
}
