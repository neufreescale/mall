package org.diwayou.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

/**
 * @author gaopeng 2021/2/3
 */
@Slf4j
public class ConfigApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        log.debug("inject global config to environment");

        environment.getPropertySources().addLast(new PropertySource<String>("global_config") {
            @Override
            public Object getProperty(String name) {
                return GlobalConfig.get(name);
            }
        });
    }
}
