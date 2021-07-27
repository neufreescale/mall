package org.diwayou.mq;

import lombok.extern.slf4j.Slf4j;
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

        ConfigHelper.injectToEnvironment("rocketmq", applicationContext.getEnvironment());

        String appName = environment.getProperty(IConfig.APP_NAME_KEY);
        ConfigHelper.injectToEnvironment(RocketmqConstants.PRODUCER_GROUP,  appName+ "_producer");
        ConfigHelper.injectToEnvironment(RocketmqConstants.CONSUMER_GROUP, appName + "_consumer");
    }
}
