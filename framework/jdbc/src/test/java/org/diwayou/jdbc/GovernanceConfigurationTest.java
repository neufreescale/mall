package org.diwayou.jdbc;

import org.apache.shardingsphere.governance.core.yaml.config.YamlGovernanceCenterConfiguration;
import org.apache.shardingsphere.governance.core.yaml.config.YamlGovernanceConfiguration;
import org.apache.shardingsphere.governance.repository.apollo.ApolloPropertyKey;
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
        YamlGovernanceCenterConfiguration registryConfig = new YamlGovernanceCenterConfiguration();
        registryConfig.setType("Zookeeper");
        registryConfig.setServerLists("localhost:2181");
        registryConfig.setProps(new Properties());
        YamlGovernanceCenterConfiguration centerConfiguration = new YamlGovernanceCenterConfiguration();
        centerConfiguration.setType("Apollo");
        centerConfiguration.setServerLists("http://apollo.test.66buy.com.cn:8080");
        Properties centerProps = new Properties();
        centerProps.put(ApolloPropertyKey.PORTAL_URL.getKey(), "http://apollo.66buy.com.cn");
        centerProps.put(ApolloPropertyKey.TOKEN.getKey(), "f07dbb959afa972b7d5c737e6d65aa95116b6080");
        centerProps.put(ApolloPropertyKey.APP_ID.getKey(), "diwayou");
        centerProps.put(ApolloPropertyKey.ADMINISTRATOR.getKey(), "apollo");
        centerProps.put(ApolloPropertyKey.ENV.getKey(), "FAT");
        centerConfiguration.setProps(centerProps);

        YamlGovernanceConfiguration configuration = new YamlGovernanceConfiguration();
        configuration.setName("op.db.test");
        configuration.setRegistryCenter(registryConfig);
        configuration.setAdditionalConfigCenter(centerConfiguration);
        configuration.setOverwrite(false);

        RootConfiguration root = new RootConfiguration();
        root.setGovernance(configuration);

        String yaml = YamlEngine.marshal(root);

        System.out.println(yaml);
    }
}
