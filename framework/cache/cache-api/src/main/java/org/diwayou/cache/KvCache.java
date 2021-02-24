package org.diwayou.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author gaopeng 2021/1/27
 */
public interface KvCache {

    void set(String key, String value);

    void set(String key, String value, long ttl);

    String get(String key);

    Boolean delete(String key);

    Boolean setIfAbsent(String key, String value, int timeout, TimeUnit timeUnit);
}
