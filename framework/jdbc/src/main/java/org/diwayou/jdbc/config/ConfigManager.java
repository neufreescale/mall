package org.diwayou.jdbc.config;

import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.diwayou.config.ConfigApi;
import org.diwayou.config.ConfigHelper;
import org.diwayou.jdbc.config.DatabaseGroupConfig.Node;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author gaopeng 2021/12/1
 */
@Slf4j
public class ConfigManager {

    private static final String GROUP_KEY = "group";

    private static final String UPDATE_KEY = GROUP_KEY + "-update";

    private static boolean needReadWriteSeparate = true;

    public static boolean isNeedReadWriteSeparate() {
        return needReadWriteSeparate;
    }

    public static void loadDataSources(String namespace, DataSourceLoadCallback loadCallback) {
        doLoadDataSources(namespace, loadCallback);

        ConfigApi.addListener(namespace, UPDATE_KEY, event -> {
            log.info("刷新数据源信息{}", event);

            synchronized (loadCallback) {
                doLoadDataSources(namespace, loadCallback);
            }
        });
    }

    private static void doLoadDataSources(String namespace, DataSourceLoadCallback loadCallback) {
        DatabaseGroupConfig groupConfig = ConfigHelper.bind(namespace, GROUP_KEY, DatabaseGroupConfig.class);
        List<Node> nodes = groupConfig.parseNodes();
        if (CollectionUtils.isEmpty(nodes)) {
            throw new IllegalArgumentException("请配置数据库节点信息");
        }

        needReadWriteSeparate = groupConfig.isNeedReadWriteSeparate();

        List<DataSource> dataSources = Lists.newArrayListWithCapacity(nodes.size());
        for (Node node : nodes) {
            DataSourceConfig dataSourceConfig = ConfigHelper.bind(namespace, node.getName(), DataSourceConfig.class);

            DataSource dataSource = createDataSource(node.getName(), dataSourceConfig);
            dataSources.add(dataSource);
        }

        loadCallback.nodes(nodes);
        loadCallback.dataSources(dataSources);
    }

    private static DataSource createDataSource(String dataSourceName, DataSourceConfig dataSourceConfig) {
        String jdbcUrl = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true",
                dataSourceConfig.getIp(), dataSourceConfig.getPort(), dataSourceConfig.getDbName());

        HikariConfig config = new HikariConfig();
        config.setPoolName(dataSourceName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(dataSourceConfig.getUserName());
        config.setPassword(dataSourceConfig.getPassword());
        config.setConnectionTimeout(TimeUnit.SECONDS.toMillis(10));
        config.setIdleTimeout(TimeUnit.SECONDS.toMillis(180));
        config.setMaxLifetime(TimeUnit.MINUTES.toMillis(20));
        config.setMinimumIdle(Integer.parseInt(dataSourceConfig.getMinimumIdle()));
        config.setMaximumPoolSize(Integer.parseInt(dataSourceConfig.getMaximumPoolSize()));
        config.setConnectionInitSql("set names utf8mb4");

        return new HikariDataSource(config);
    }
}
