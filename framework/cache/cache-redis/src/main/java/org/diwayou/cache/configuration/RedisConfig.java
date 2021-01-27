package org.diwayou.cache.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/1/27
 */
@NoArgsConstructor
@Data
public class RedisConfig {

    /**
     * sentinel mode
     */
    private String master = "";

    /**
     * nodes=127.0.0.1:23679,127.0.0.1:23680,127.0.0.1:23681
     */
    private String nodes = "localhost:6379";

    private String password = "";

    private int database = 0;

    /**
     * cluster mode
     */
    private int maxRedirects = 5;
}
