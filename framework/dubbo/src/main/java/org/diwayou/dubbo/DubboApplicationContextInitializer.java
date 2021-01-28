package org.diwayou.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.diwayou.config.ConfigHelper;
import org.diwayou.config.IConfig;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

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
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String ns = environment.getProperty("dubbo.namespace");
        if (StringUtils.isBlank(ns)) {
            log.warn("No dubbo.namespace but has dubbo dependency");
            return;
        }

        log.info("dubbo init namespace={}", ns);

        ConfigHelper.injectToEnvironment(ns, "config", environment);
        ConfigHelper.injectToEnvironment(ns, environment.getProperty(IConfig.APP_NAME_KEY), environment);
    }
}
