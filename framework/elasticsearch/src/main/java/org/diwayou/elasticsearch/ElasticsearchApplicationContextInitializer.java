package org.diwayou.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.config.ConfigHelper;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author gaopeng 2021/7/27
 */
@Slf4j
public class ElasticsearchApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigHelper.injectToEnvironment("elasticsearch", applicationContext.getEnvironment());
    }
}
