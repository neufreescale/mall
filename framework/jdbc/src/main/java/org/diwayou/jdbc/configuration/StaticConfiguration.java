package org.diwayou.jdbc.configuration;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.infra.yaml.config.YamlConfiguration;
import org.apache.shardingsphere.infra.yaml.config.YamlRuleConfiguration;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

/**
 * @author gaopeng 2021/1/21
 */
@Getter
@Setter
public class StaticConfiguration implements YamlConfiguration {

    private Map<String, Map<String, Object>> dataSources;

    private Collection<YamlRuleConfiguration> rules = new LinkedList<>();

    private Properties props = new Properties();
}
