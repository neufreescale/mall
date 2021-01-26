package org.diwayou.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;

import java.util.Collections;

/**
 * @author gaopeng 2021/1/19
 */
public class ApolloConfig implements IConfig {

    @Override
    public String getProperty(String namespace, String key) {
        Config config = ConfigService.getConfig(namespace);

        return config.getProperty(key, null);
    }

    @Override
    public void addListener(String namespace, String key, ConfigListener listener) {
        Config config = ConfigService.getConfig(namespace);

        config.addChangeListener(changeEvent -> {
            ConfigChange change = changeEvent.getChange(key);
            if (change != null) {
                ConfigEvent event = new ConfigEvent(namespace, key, change.getNewValue());
                listener.onChange(event);
            }
        }, Collections.singleton(key));
    }
}
