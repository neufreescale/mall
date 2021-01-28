package org.diwayou.jdbc;

import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.driver.governance.internal.datasource.GovernanceShardingSphereDataSource;
import org.apache.shardingsphere.driver.governance.internal.util.YamlGovernanceRepositoryConfigurationSwapperUtil;
import org.apache.shardingsphere.governance.core.yaml.swapper.DataSourceConfigurationYamlSwapper;
import org.apache.shardingsphere.governance.repository.api.config.GovernanceConfiguration;
import org.apache.shardingsphere.infra.config.RuleConfiguration;
import org.apache.shardingsphere.infra.config.datasource.DataSourceConfiguration;
import org.apache.shardingsphere.infra.config.datasource.DataSourceConverter;
import org.apache.shardingsphere.infra.yaml.engine.YamlEngine;
import org.apache.shardingsphere.infra.yaml.swapper.YamlRuleConfigurationSwapperEngine;
import org.diwayou.config.ConfigApi;
import org.diwayou.jdbc.configuration.RootConfiguration;
import org.diwayou.jdbc.configuration.StaticConfiguration;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gaopeng 2021/1/20
 */
public class DatabaseFactory {

    private static final String KEY = "db";

    public DataSource create(String namespace) throws IOException, SQLException {
        String yaml = ConfigApi.getProperty(namespace, KEY);
        if (StringUtils.isBlank(yaml)) {
            throw new RuntimeException(String.format("yaml is empty %s %s", namespace, KEY));
        }

        RootConfiguration rootConfiguration = YamlEngine.unmarshal(yaml, RootConfiguration.class);
        if (rootConfiguration.getStaticConfig() != null) {
            StaticConfiguration staticConfiguration = rootConfiguration.getStaticConfig();
            Map<String, DataSourceConfiguration> dataSourceConfigs = staticConfiguration.getDataSources().entrySet().stream().collect(
                    Collectors.toMap(Map.Entry::getKey,
                            entry -> new DataSourceConfigurationYamlSwapper().swapToObject(entry.getValue()),
                            (oldValue, currentValue) -> oldValue,
                            LinkedHashMap::new));
            Map<String, DataSource> dataSourceMap = DataSourceConverter.getDataSourceMap(dataSourceConfigs);

            Collection<RuleConfiguration> ruleConfigurations = new YamlRuleConfigurationSwapperEngine().swapToRuleConfigurations(staticConfiguration.getRules());

            return ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, ruleConfigurations, staticConfiguration.getProps());
        }

        if (rootConfiguration.getGovernance() != null) {
            GovernanceConfiguration configuration = YamlGovernanceRepositoryConfigurationSwapperUtil.marshal(rootConfiguration.getGovernance());

            return new GovernanceShardingSphereDataSource(configuration);
        }

        throw new RuntimeException("DataSource config illegal " + yaml);
    }

}
