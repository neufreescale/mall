package org.diwayou.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Objects;

/**
 * @author gaopeng 2021/1/27
 */
@Configuration(proxyBeanMethods = false)
public class ApolloConfigApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        System.setProperty("app.id", Objects.requireNonNull(environment.getProperty(IConfig.APP_NAME_KEY)));
    }
}
