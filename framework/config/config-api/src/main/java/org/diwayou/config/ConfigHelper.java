package org.diwayou.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        String content = ConfigApi.getProperty(namespace, key);
        if (StringUtils.isBlank(content)) {
            log.warn("content is empty namespace={},key={}", namespace, key);
            return;
        }

        Properties properties = new Properties();
        try {
            properties.load(new StringReader(content));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        configurableEnvironment.getPropertySources().addLast(new PropertiesPropertySource(namespace + "_" + key, properties));
    }

    public static void injectToEnvironment(String propertyName, String propertyValue) {
        GlobalConfig.put(propertyName, propertyValue);
    }
}
