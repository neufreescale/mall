package org.diwayou.jdbc.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.diwayou.jdbc.config.ConfigManager;
import org.diwayou.jdbc.config.DataSourceLoadCallback;
import org.diwayou.jdbc.config.DatabaseGroupConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author gaopeng 2021/11/19
 */
@Slf4j
@Getter
@Setter
public class EmallDataSource extends AbstractDataSource implements InitializingBean, DisposableBean {

    private String namespace;

    private volatile List<DataSource> dataSources;

    private volatile int[] weights;

    public EmallDataSource() {
    }

    public EmallDataSource(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public void afterPropertiesSet() {
        ConfigManager.loadDataSources(namespace, new DataSourceLoadCallback() {
            @Override
            public void dataSources(List<DataSource> newDataSources) {
                try {
                    refreshDataSources(newDataSources);
                    log.info("刷新数据源成功{}", dataSources);
                } catch (Exception e) {
                    log.error("刷新数据源失败", e);
                }
            }

            @Override
            public void nodes(List<DatabaseGroupConfig.Node> nodes) {
                int[] newWeights = new int[nodes.size()];
                for (int i = 0; i < newWeights.length; i++) {
                    newWeights[i] = nodes.get(i).getWeight();
                }

                weights = newWeights;
            }
        });
    }

    private void refreshDataSources(List<DataSource> newDataSources) {
        List<DataSource> oldDataSources = dataSources;
        dataSources = newDataSources;

        close(oldDataSources);
    }

    protected DataSource determineTargetDataSource() throws SQLException {
        if (CollectionUtils.isEmpty(dataSources)) {
            throw new SQLException("找不到可用数据源");
        }

        int index;
        if (HintManager.autoRoute()) {
            index = autoRoute();
        } else {
            index = HintManager.getDataSourceIndex();
        }

        return dataSources.get(index);
    }

    private int autoRoute() {
        if (ArrayUtils.isEmpty(weights)) {
            return randomRoute();
        }

        int[] areaEnds = new int[weights.length];
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i];
            areaEnds[i] = sum;
        }

        int rand = ThreadLocalRandom.current().nextInt(sum);
        for (int i = 0; i < areaEnds.length; i++) {
            if (rand < areaEnds[i]) {
                return i;
            }
        }

        return randomRoute();
    }

    private int randomRoute() {
        return ThreadLocalRandom.current().nextInt(dataSources.size());
    }

    @Override
    public Connection getConnection() throws SQLException {
        for (int i = 0; i < 2; i++) {
            try {
                return determineTargetDataSource().getConnection();
            } catch (Exception e) {
                log.error("获取链接失败", e);
            }
        }

        // 多次失败默认走主库
        return dataSources.get(HintManager.MASTER_INDEX).getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return determineTargetDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface));
    }

    private void close(List<DataSource> dataSourceList) {
        if (dataSourceList != null) {
            dataSourceList.stream()
                    .map(ds -> (HikariDataSource) ds)
                    .forEach(HikariDataSource::close);
            dataSourceList.clear();
        }
    }

    @Override
    public void destroy() {
        close(dataSources);
    }
}
