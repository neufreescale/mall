package org.diwayou.cache.configuration;

import org.apache.commons.lang3.StringUtils;
import org.diwayou.config.ConfigApi;
import org.diwayou.core.yml.YamlUtil;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author gaopeng 2021/1/27
 */
public class RedisFactory {

    private static final String DB_KEY = "db";
    private static final String CLIENT_KEY = "client";

    public RedisConnectionFactory create(String namespace) {
        String dbYaml = ConfigApi.getProperty(namespace, DB_KEY);
        if (StringUtils.isBlank(dbYaml)) {
            throw new RuntimeException(String.format("yaml is empty %s %s", namespace, DB_KEY));
        }

        RedisConfig redisConfig = YamlUtil.unmarshal(dbYaml, RedisConfig.class);

        String clientYaml = ConfigApi.getProperty(namespace, CLIENT_KEY);
        RedisClientConfig redisClientConfig = new RedisClientConfig();
        if (StringUtils.isNotBlank(clientYaml)) {
            redisClientConfig = YamlUtil.unmarshal(clientYaml, RedisClientConfig.class);
        }

        return new RedisConnectionFactoryBuilder(redisConfig, redisClientConfig).build();
    }
}
