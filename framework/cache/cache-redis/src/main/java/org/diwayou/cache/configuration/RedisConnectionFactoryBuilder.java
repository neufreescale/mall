package org.diwayou.cache.configuration;

import com.google.common.collect.Iterables;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Set;

import static org.springframework.util.StringUtils.split;

/**
 * @author gaopeng 2021/1/27
 */
public class RedisConnectionFactoryBuilder {

    private RedisConfig redisConfig;

    private RedisClientConfig redisClientConfig;

    private final Set<String> nodeSet;

    public RedisConnectionFactoryBuilder(RedisConfig redisConfig, RedisClientConfig redisClientConfig) {
        this.redisConfig = redisConfig;
        this.redisClientConfig = redisClientConfig;

        nodeSet = StringUtils.commaDelimitedListToSet(redisConfig.getNodes());
    }

    public RedisConnectionFactory build() {
        return new LettuceConnectionFactory(redisConfiguration(), clientConfiguration());
    }

    private RedisConfiguration redisConfiguration() {
        if (StringUtils.hasText(redisConfig.getMaster())) {
            return sentinelConfiguration();
        } else if (nodeSet.size() == 1) {
            return standaloneConfiguration();
        } else {
            return clusterConfiguration();
        }
    }

    private RedisStandaloneConfiguration standaloneConfiguration() {
        String[] args = split(Iterables.getFirst(nodeSet, ""), ":");

        Assert.notNull(args, "HostAndPort need to be separated by  ':'.");
        Assert.isTrue(args.length == 2, "Host and Port String needs to specified as host:port");

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(args[0]);
        if (StringUtils.hasText(redisConfig.getPassword())) {
            configuration.setPassword(redisConfig.getPassword());
        }
        configuration.setPort(Integer.parseInt(args[1]));
        configuration.setDatabase(redisConfig.getDatabase());

        return configuration;
    }

    private RedisSentinelConfiguration sentinelConfiguration() {
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration(redisConfig.getMaster(), nodeSet);
        if (StringUtils.hasText(redisConfig.getPassword())) {
            configuration.setPassword(redisConfig.getPassword());
        }
        configuration.setDatabase(redisConfig.getDatabase());

        return configuration;
    }

    private RedisClusterConfiguration clusterConfiguration() {
        RedisClusterConfiguration configuration = new RedisClusterConfiguration(nodeSet);
        if (StringUtils.hasText(redisConfig.getPassword())) {
            configuration.setPassword(redisConfig.getPassword());
        }
        configuration.setMaxRedirects(redisConfig.getMaxRedirects());

        return configuration;
    }

    private LettuceClientConfiguration clientConfiguration() {
        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(redisClientConfig.getPoolMaxIdle());
        poolConfig.setMinIdle(redisClientConfig.getPoolMinIdle());
        poolConfig.setMaxTotal(redisClientConfig.getPoolMaxActive());
        poolConfig.setMaxWaitMillis(redisClientConfig.getPoolMaxWait());
        poolConfig.setTestOnBorrow(true);

        return LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .commandTimeout(Duration.ofMillis(redisClientConfig.getTimeout()))
                .build();
    }
}
