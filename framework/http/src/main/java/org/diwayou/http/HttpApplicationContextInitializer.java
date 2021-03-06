package org.diwayou.http;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.config.ConfigHelper;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author gaopeng 2021/3/19
 */
@Slf4j
public class HttpApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigHelper.injectToEnvironment("http", applicationContext.getEnvironment());
    }
}
