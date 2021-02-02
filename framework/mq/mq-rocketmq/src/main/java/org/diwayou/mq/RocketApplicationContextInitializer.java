package org.diwayou.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.diwayou.config.ConfigHelper;
import org.diwayou.config.IConfig;
import org.diwayou.mq.util.RocketmqConstants;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author gaopeng 2021/2/3
 */
@Slf4j
public class RocketApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String ns = environment.getProperty("rocketmq.namespace");
        if (StringUtils.isBlank(ns)) {
            log.warn("No rocketmq.namespace but has rocketmq dependency");
            return;
        }

        log.info("rocketmq init namespace={}", ns);

        ConfigHelper.injectToEnvironment(ns, "config", environment);

        ConfigHelper.injectToEnvironment(RocketmqConstants.PRODUCER_GROUP, environment.getProperty(IConfig.APP_NAME_KEY) + "_producer");
        ConfigHelper.injectToEnvironment(RocketmqConstants.CONSUMER_GROUP, environment.getProperty(IConfig.APP_NAME_KEY) + "_consumer");
    }
}
