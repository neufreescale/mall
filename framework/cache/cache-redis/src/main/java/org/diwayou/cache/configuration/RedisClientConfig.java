package org.diwayou.cache.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/1/27
 */
@NoArgsConstructor
@Data
public class RedisClientConfig {

    private int poolMaxActive = 100;

    private int poolMaxWait = 1000;

    private int poolMaxIdle = 4;

    private int poolMinIdle = 1;

    private int timeout = 3000;
}
