package org.diwayou.config;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author gaopeng 2021/2/3
 */
public class GlobalConfig {

    private static final Map<String, String> GLOBAL_CONFIG = Maps.newConcurrentMap();

    public static void put(String key, String value) {
        GLOBAL_CONFIG.put(key, value);
    }

    public static String get(String key) {
        return GLOBAL_CONFIG.get(key);
    }
}
