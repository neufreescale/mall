package org.diwayou.config.impl;

import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.diwayou.config.ConfigListener;
import org.diwayou.config.Env;
import org.diwayou.config.IConfig;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author gaopeng 2021/1/26
 */
public class LocalFileConfig implements IConfig {

    @Override
    public String getProperty(String namespace, String key) {
        File file = new File(buildFilePath(namespace, key));
        if (!file.exists() || !file.canRead()) {
            return null;
        }

        try {
            return Files.asCharSource(file, StandardCharsets.UTF_8).read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addListener(String namespace, String key, ConfigListener listener) {
        // todo
    }

    @Override
    public Env env() {
        String env = getProperty(Constants.NS_COMMON, Constants.KEY_ENV);
        if (StringUtils.isBlank(env)) {
            return Env.Dev;
        }

        return Env.valueOf(env);
    }

    private static String buildFilePath(String namespace, String key) {
        return dir + File.separator + namespace + File.separator + key;
    }

    public static final String dir;
    static {
        dir = System.getProperty("config.dir", "config");
    }
}
