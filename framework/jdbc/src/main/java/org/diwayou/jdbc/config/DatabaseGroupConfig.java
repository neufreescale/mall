package org.diwayou.jdbc.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaopeng 2021/12/1
 */
@Data
public class DatabaseGroupConfig {

    /**
     * 一组数据库节点，例如mysql的主备模式多个节点，或者tidb的多个TiDB节点地址，当是tidb的时候，多个节点都可写入
     * 格式：nodeName1:weight,nodeName2:weight等，其中weight是整数，不需要加起来是100
     * 其中mysql由于只能写入主库，所以只有读的时候才会走这个权重，所以主库权重可以小一点，例如master:20,slave1:40,slave2:40
     * tidb由于TiDB各个节点都可以进行读写，所以权重可以按照机器配置来配置权重，例如node1:30,node2:30,node3:30
     */
    private String nodes;

    /**
     * 是否需要读写分离，默认为true
     * 例如mysql只能写主库，就需要读写分离，而tidb这种TiDB Server既支持读也支持写，就不需要读写分离
     */
    private boolean needReadWriteSeparate = true;

    public List<Node> parseNodes() {
        if (StringUtils.isBlank(nodes)) {
            return Collections.emptyList();
        }

        String[] nodeArr = nodes.split(",");

        return Arrays.stream(nodeArr)
                .map(Node::parse)
                .collect(Collectors.toList());
    }

    @Data
    public static class Node {

        private String name;

        private int weight = 10;

        public Node(String name) {
            this.name = name;
        }

        public Node(String name, int weight) {
            this.name = name;
            this.weight = weight;
        }

        public static Node parse(String config) {
            if (StringUtils.isBlank(config)) {
                throw new IllegalArgumentException("node信息不能为空");
            }
            String[] nameWeight = config.split(":");
            if (nameWeight.length == 1) {
                return new Node(nameWeight[0]);
            } else if (nameWeight.length == 2) {
                return new Node(nameWeight[0], Integer.parseInt(nameWeight[1]));
            }

            throw new IllegalArgumentException("node配置格式不正确，如name:weight");
        }
    }
}
