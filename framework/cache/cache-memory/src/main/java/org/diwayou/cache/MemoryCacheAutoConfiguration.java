package org.diwayou.cache;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.cache.impl.MemoryHashCache;
import org.diwayou.cache.impl.MemoryKvCache;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * @author gaopeng 2021/1/27
 */
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Slf4j
public class MemoryCacheAutoConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public MemoryKvCache memoryKvCache() {
        return new MemoryKvCache();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public MemoryHashCache memoryHashCache() {
        return new MemoryHashCache();
    }
}
