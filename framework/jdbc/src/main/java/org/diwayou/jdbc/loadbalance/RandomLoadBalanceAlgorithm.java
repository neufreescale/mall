package org.diwayou.jdbc.loadbalance;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.replicaquery.spi.ReplicaLoadBalanceAlgorithm;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author gaopeng 2021/1/20
 */
@Getter
@Setter
public class RandomLoadBalanceAlgorithm implements ReplicaLoadBalanceAlgorithm {

    private Properties props = new Properties();

    @Override
    public String getType() {
        return "C-RANDOM";
    }

    @Override
    public String getDataSource(final String name, final String masterDataSourceName, final List<String> slaveDataSourceNames) {
        if (CollectionUtils.isEmpty(slaveDataSourceNames)) {
            return masterDataSourceName;
        }

        return slaveDataSourceNames.get(ThreadLocalRandom.current().nextInt(slaveDataSourceNames.size()));
    }
}
