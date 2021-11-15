package org.diwayou.jdbc;

import org.apache.shardingsphere.infra.yaml.config.pojo.mode.YamlModeConfiguration;
import org.apache.shardingsphere.infra.yaml.config.pojo.mode.YamlPersistRepositoryConfiguration;
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
        Properties properties = new Properties();
        properties.setProperty("server-lists", "localhost:2181");
        properties.setProperty("namespace", "op.db.test");
        YamlPersistRepositoryConfiguration repositoryConfiguration = new YamlPersistRepositoryConfiguration();
        repositoryConfiguration.setType("Zookeeper");
        repositoryConfiguration.setProps(properties);

        YamlModeConfiguration modeConfiguration = new YamlModeConfiguration();
        modeConfiguration.setOverwrite(false);
        modeConfiguration.setType("Cluster");
        modeConfiguration.setRepository(repositoryConfiguration);

        RootConfiguration root = new RootConfiguration();
        root.setDynamicConfig(modeConfiguration);

        String yaml = YamlEngine.marshal(root);

        System.out.println(yaml);
    }
}
