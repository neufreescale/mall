package org.diwayou.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * @author gaopeng 2021/1/27
 */
@Configuration(proxyBeanMethods = false)
public class ApolloConfigAutoConfiguration implements EnvironmentAware {

    @Override
    public void setEnvironment(Environment environment) {
        System.setProperty("app.id", Objects.requireNonNull(environment.getProperty(IConfig.APP_NAME_KEY)));
    }
}
