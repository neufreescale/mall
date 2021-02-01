package org.diwayou.cache;

/**
 * @author gaopeng 2021/1/27
 */
public interface KvCache {

    void set(String key, String value);

    void set(String key, String value, long ttl);

    String get(String key);

    Boolean delete(String key);
}
