package org.diwayou.config;

/**
 * @author gaopeng 2021/1/19
 */
public interface IConfig {

    String APP_NAME_KEY = "appName";

    String getProperty(String namespace, String key);

    void addListener(String namespace, String key, ConfigListener listener);
}
