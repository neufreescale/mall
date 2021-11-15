package org.diwayou.jdbc.configuration;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.infra.yaml.config.pojo.mode.YamlModeConfiguration;

import java.util.Properties;

/**
 * @author gaopeng 2021/1/21
 */
@Getter
@Setter
public class RootConfiguration {

    private YamlModeConfiguration dynamicConfig;

    private StaticConfiguration staticConfig;

    private Properties props = new Properties();

}
