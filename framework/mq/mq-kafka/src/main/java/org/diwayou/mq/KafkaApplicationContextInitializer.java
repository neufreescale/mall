package org.diwayou.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.diwayou.config.ConfigHelper;
import org.diwayou.config.IConfig;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author gaopeng 2021/2/2
 */
@Slf4j
public class KafkaApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String ns = environment.getProperty("kafka.namespace");
        if (StringUtils.isBlank(ns)) {
            log.warn("No kafka.namespace but has kafka dependency");
            return;
        }

        log.info("kafka init namespace={}", ns);

        ConfigHelper.injectToEnvironment(ns, "config", environment);
        ConfigHelper.injectToEnvironment("spring.kafka.consumer.group-id", environment.getProperty(IConfig.APP_NAME_KEY));
    }
}
