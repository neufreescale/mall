package org.diwayou.jdbc;

import org.apache.shardingsphere.governance.core.yaml.config.pojo.YamlGovernanceConfiguration;
import org.apache.shardingsphere.governance.core.yaml.config.pojo.YamlRegistryCenterConfiguration;
import org.apache.shardingsphere.infra.yaml.engine.YamlEngine;
import org.diwayou.jdbc.configuration.RootConfiguration;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * @author gaopeng 2021/1/20
 */
public class GovernanceConfigurationTest {

    @Test
    public void governanceConfigTest() throws IOException {
        YamlRegistryCenterConfiguration registryConfig = new YamlRegistryCenterConfiguration();
        registryConfig.setType("Zookeeper");
        registryConfig.setServerLists("localhost:2181");
        registryConfig.setProps(new Properties());

        YamlGovernanceConfiguration configuration = new YamlGovernanceConfiguration();
        configuration.setName("op.db.test");
        configuration.setRegistryCenter(registryConfig);
        configuration.setOverwrite(false);

        RootConfiguration root = new RootConfiguration();
        root.setGovernance(configuration);

        String yaml = YamlEngine.marshal(root);

        System.out.println(yaml);
    }
}
