package org.diwayou.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.config.ConfigHelper;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author gaopeng 2021/1/29
 */
@Slf4j
public class DubboApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static {
        System.setProperty("dubbo.application.logger", "slf4j");
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigHelper.injectToEnvironment("dubbo", applicationContext.getEnvironment());
    }
}
