package org.diwayou.jdbc.loadbalance;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.replicaquery.spi.ReplicaLoadBalanceAlgorithm;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author gaopeng 2021/1/20
 */
@Getter
@Setter
public class PriorityLoadBalanceAlgorithm implements ReplicaLoadBalanceAlgorithm {

    private static final String KEY = "weights";

    private Properties props = new Properties();

    private int[] weights;

    @Override
    public String getDataSource(String name, String masterDataSourceName, List<String> slaveDataSourceNames) {
        if (weights.length != slaveDataSourceNames.size() + 1) {
            return random(slaveDataSourceNames);
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
                if (i == 0) {
                    return masterDataSourceName;
                } else {
                    return slaveDataSourceNames.get(i - 1);
                }
            }
        }

        return random(slaveDataSourceNames);
    }

    private static String random(List<String> slaveDataSourceNames) {
        return slaveDataSourceNames.get(ThreadLocalRandom.current().nextInt(slaveDataSourceNames.size()));
    }

    @Override
    public String getType() {
        return "WEIGHT";
    }

    @Override
    public void setProps(Properties props) {
        this.props = props;

        String weightsValue = props.getProperty(KEY, "");
        if (StringUtils.isBlank(weightsValue)) {
            throw new RuntimeException("必须配置参数" + KEY);
        }

        String[] weightsStr = StringUtils.split(weightsValue, ",");
        this.weights = new int[weightsStr.length];
        for (int i = 0; i < weightsStr.length; i++) {
            this.weights[i] = Integer.parseInt(weightsStr[i]);
        }
    }
}
