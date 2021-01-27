package org.diwayou.cache;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.cache.impl.MemoryHashCache;
import org.diwayou.cache.impl.MemoryKvCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaopeng 2021/1/27
 */
@Configuration
@Slf4j
public class MemoryCacheAutoConfiguration {

    @Bean
    public MemoryKvCache memoryKvCache() {
        return new MemoryKvCache();
    }

    @Bean
    public MemoryHashCache memoryHashCache() {
        return new MemoryHashCache();
    }
}
