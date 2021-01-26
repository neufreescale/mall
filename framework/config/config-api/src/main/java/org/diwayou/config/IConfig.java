package org.diwayou.config;

import org.pf4j.ExtensionPoint;

/**
 * @author gaopeng 2021/1/19
 */
public interface IConfig extends ExtensionPoint {

    String getProperty(String namespace, String key);

    void addListener(String namespace, String key, ConfigListener listener);
}
