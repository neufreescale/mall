package org.diwayou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaopeng 2021/1/27
 */
@Configuration(proxyBeanMethods = false)
public class ApolloConfigAutoConfiguration {

    @Bean
    public ApolloConfig apolloConfig() {
        return new ApolloConfig();
    }
}
