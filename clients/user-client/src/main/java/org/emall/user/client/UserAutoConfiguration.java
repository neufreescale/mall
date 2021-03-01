package org.emall.user.client;

import org.diwayou.security.api.UserRetriever;
import org.emall.user.client.security.BaseUserRetrieve;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaopeng 2021/3/2
 */
@Configuration
public class UserAutoConfiguration {

    @Bean
    public UserRetriever baseUserRetrieve() {
        return new BaseUserRetrieve();
    }
}
