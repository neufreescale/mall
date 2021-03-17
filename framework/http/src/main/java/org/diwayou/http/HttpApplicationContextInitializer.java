package org.diwayou.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.diwayou.config.ConfigHelper;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author gaopeng 2021/3/19
 */
@Slf4j
public class HttpApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String ns = environment.getProperty("http.namespace");
        if (StringUtils.isBlank(ns)) {
            log.warn("No http.namespace but has http dependency");
            return;
        }

        log.info("http init namespace={}", ns);

        ConfigHelper.injectToEnvironment(ns, "config", environment);
    }
}
