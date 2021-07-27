package org.diwayou.core.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaopeng 2021/7/27
 */
@Configuration
public class ServiceConfiguration {

    @Bean
    public ServiceInfoPrinter serviceInfoPrinter() {
        return new ServiceInfoPrinter();
    }
}
