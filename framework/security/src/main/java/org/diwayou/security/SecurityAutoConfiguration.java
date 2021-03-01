package org.diwayou.security;

import org.diwayou.security.api.UserRetriever;
import org.diwayou.security.core.TokenAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * @author gaopeng 2021/3/1
 */
@Configuration
@Import(SecurityConfiguration.class)
public class SecurityAutoConfiguration {

    @Bean
    TokenAuthenticationProvider tokenAuthenticationProvider(List<UserRetriever> userRetrievers) {
        return new TokenAuthenticationProvider(userRetrievers);
    }
}
