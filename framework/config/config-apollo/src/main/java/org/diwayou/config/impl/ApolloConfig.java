package org.diwayou.config.impl;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.enums.EnvUtils;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.foundation.Foundation;
import org.diwayou.config.ConfigEvent;
import org.diwayou.config.ConfigListener;
import org.diwayou.config.Env;
import org.diwayou.config.IConfig;

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

    @Override
    public Env env() {
        switch (EnvUtils.transformEnv(Foundation.server().getEnvType())) {
            case DEV:
                return Env.Dev;
            case FAT:
                return Env.Test;
            case UAT:
                return Env.Pre;
            case PRO:
                return Env.Online;
            default:
                throw new IllegalStateException("环境配置有问题");
        }
    }
}
