package org.diwayou.jdbc.config;

import org.diwayou.jdbc.config.DatabaseGroupConfig.Node;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author gaopeng 2021/11/25
 */
public interface DataSourceLoadCallback {

    /**
     * 读取数据源列表
     * @param newDataSources 新读取的数据源列表
     */
    void dataSources(List<DataSource> newDataSources);

    /**
     * 读取数据源节点列表
     * @param nodes 新的数据源节点配置信息
     */
    void nodes(List<Node> nodes);
}
