package org.diwayou.mq;

import lombok.extern.slf4j.Slf4j;
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

        ConfigHelper.injectToEnvironment("kafka", applicationContext.getEnvironment());
        ConfigHelper.injectToEnvironment("spring.kafka.consumer.group-id", environment.getProperty(IConfig.APP_NAME_KEY));
    }
}
