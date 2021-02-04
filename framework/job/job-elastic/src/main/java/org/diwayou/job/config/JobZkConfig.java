package org.diwayou.job.config;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/2/4
 */
@Data
@NoArgsConstructor
public class JobZkConfig {

    private String serverLists;

    /**
     * Namespace.
     */
    private String namespace;

    /**
     * Base sleep time milliseconds.
     */
    private int baseSleepTimeMilliseconds = 1000;

    /**
     * Max sleep time milliseconds.
     */
    private int maxSleepTimeMilliseconds = 3000;

    /**
     * Max retry times.
     */
    private int maxRetries = 3;

    /**
     * Session timeout milliseconds.
     */
    private int sessionTimeoutMilliseconds;

    /**
     * Connection timeout milliseconds.
     */
    private int connectionTimeoutMilliseconds;

    /**
     * Zookeeper digest.
     */
    private String digest;
}
