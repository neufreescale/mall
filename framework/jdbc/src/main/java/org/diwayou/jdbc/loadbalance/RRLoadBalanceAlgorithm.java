package org.diwayou.jdbc.loadbalance;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.readwritesplitting.spi.ReplicaLoadBalanceAlgorithm;

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
    public String getDataSource(final String name, final String writeDataSourceName, final List<String> readDataSourceNames) {
        if (CollectionUtils.isEmpty(readDataSourceNames)) {
            return writeDataSourceName;
        }

        AtomicInteger count = COUNTS.containsKey(name) ? COUNTS.get(name) : new AtomicInteger(0);
        COUNTS.putIfAbsent(name, count);
        count.compareAndSet(readDataSourceNames.size(), 0);
        return readDataSourceNames.get(Math.abs(count.getAndIncrement()) % readDataSourceNames.size());
    }
}
