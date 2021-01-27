package org.diwayou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaopeng 2021/1/27
 */
@Configuration(proxyBeanMethods = false)
public class LocalFileConfigAutoConfiguration {

    @Bean
    public LocalFileConfig localFileConfig() {
        return new LocalFileConfig();
    }
}
