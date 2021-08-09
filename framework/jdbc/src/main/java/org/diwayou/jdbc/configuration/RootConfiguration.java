package org.diwayou.jdbc.configuration;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.governance.core.yaml.config.pojo.YamlGovernanceConfiguration;

import java.util.Properties;

/**
 * @author gaopeng 2021/1/21
 */
@Getter
@Setter
public class RootConfiguration {

    private YamlGovernanceConfiguration governance;

    private StaticConfiguration staticConfig;

    private Properties props = new Properties();

}
