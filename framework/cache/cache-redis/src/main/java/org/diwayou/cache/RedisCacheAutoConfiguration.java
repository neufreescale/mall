package org.diwayou.cache;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.cache.configuration.RedisFactory;
import org.diwayou.cache.impl.RedisHashCache;
import org.diwayou.cache.impl.RedisKvCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author gaopeng 2021/1/27
 */
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@ConditionalOnProperty(prefix = "redis", value = "namespace")
@Slf4j
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisCacheAutoConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public RedisFactory redisFactory() {
        return new RedisFactory();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Primary
    @ConditionalOnMissingBean(name = "redisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory(RedisFactory redisFactory) {
        String ns = environment.getProperty("redis.namespace");

        log.info("auto config redis with namespace {}", ns);

        return redisFactory.create(ns);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public RedisKvCache redisKvCache() {
        return new RedisKvCache();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public RedisHashCache redisHashCache() {
        return new RedisHashCache();
    }
}
