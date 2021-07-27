package org.diwayou.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * @author gaopeng 2021/1/29
 */
@Slf4j
public class ConfigHelper {

    public static void injectToEnvironment(String namespace, String key, ConfigurableEnvironment configurableEnvironment) {
        Properties properties = parseToProperties(namespace, key);

        if (properties.isEmpty()) {
            return;
        }

        configurableEnvironment.getPropertySources().addLast(new PropertiesPropertySource(namespace + "_" + key, properties));
    }

    public static void injectToEnvironment(String propertyName, String propertyValue) {
        GlobalConfig.put(propertyName, propertyValue);
    }

    public static void injectToEnvironment(String nsKeyPrefix, ConfigurableEnvironment environment) {
        String nsKey = nsKeyPrefix + ".namespace";

        String ns = environment.getProperty(nsKey);
        if (StringUtils.isBlank(ns)) {
            log.warn("No {} but has {} dependency", nsKey, nsKeyPrefix);
            return;
        }

        log.info("{} init namespace={}", nsKeyPrefix, ns);

        ConfigHelper.injectToEnvironment(ns, "config", environment);
        ConfigHelper.injectToEnvironment(ns, environment.getProperty(IConfig.APP_NAME_KEY), environment);
    }

    public static Properties parseToProperties(String namespace, String key) {
        String content = ConfigApi.getProperty(namespace, key);
        if (StringUtils.isBlank(content)) {
            log.info("content is empty namespace={}, key={}", namespace, key);
            return new Properties();
        }

        Properties properties = new Properties();
        try {
            properties.load(new StringReader(content));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    public static <T> T bind(String namespace, String key, Class<T> type) {
        Properties properties = parseToProperties(namespace, key);

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));

        return binder.bindOrCreate("", type);
    }
}
