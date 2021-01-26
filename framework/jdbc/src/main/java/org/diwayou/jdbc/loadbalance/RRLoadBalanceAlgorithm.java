package org.diwayou.jdbc.loadbalance;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.replicaquery.spi.ReplicaLoadBalanceAlgorithm;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gaopeng 2021/1/20
 */
@Getter
@Setter
public class RRLoadBalanceAlgorithm implements ReplicaLoadBalanceAlgorithm {

    private static final ConcurrentHashMap<String, AtomicInteger> COUNTS = new ConcurrentHashMap<>();

    private Properties props = new Properties();

    @Override
    public String getType() {
        return "RR";
    }

    @Override
    public String getDataSource(final String name, final String masterDataSourceName, final List<String> slaveDataSourceNames) {
        if (CollectionUtils.isEmpty(slaveDataSourceNames)) {
            return masterDataSourceName;
        }

        AtomicInteger count = COUNTS.containsKey(name) ? COUNTS.get(name) : new AtomicInteger(0);
        COUNTS.putIfAbsent(name, count);
        count.compareAndSet(slaveDataSourceNames.size(), 0);
        return slaveDataSourceNames.get(Math.abs(count.getAndIncrement()) % slaveDataSourceNames.size());
    }
}
