package org.diwayou.cache.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author gaopeng 2021/2/18
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class CacheData {

    public static CacheData create(String value) {
        return new CacheData(value, Long.MAX_VALUE);
    }

    public static CacheData create(String value, long ttl) {
        return new CacheData(value, ttl);
    }

    private final String value;

    /**
     * millis
     */
    private final long ttl;
}
