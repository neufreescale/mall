package org.diwayou.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.diwayou.config.ConfigHelper;
import org.diwayou.core.bean.BeanUtil;
import org.diwayou.job.config.JobZkConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * @author gaopeng 2021/2/4
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "job", value = "namespace")
@Import({SimpleJobConfiguration.class})
@Slf4j
public class JobAutoConfiguration implements EnvironmentAware {

    private Environment environment;

    @Bean
    public ZookeeperRegistryCenter zookeeperRegistryCenter() {
        String ns = environment.getProperty("job.namespace");
        log.info("job init namespace={}", ns);

        JobZkConfig jobZkConfig = ConfigHelper.bind(ns, "config", JobZkConfig.class);
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(jobZkConfig.getServerLists(), jobZkConfig.getNamespace());
        BeanUtil.copyProperties(jobZkConfig, zkConfig);

        ZookeeperRegistryCenter coordinatorRegistryCenter = new ZookeeperRegistryCenter(zkConfig);
        coordinatorRegistryCenter.init();

        return coordinatorRegistryCenter;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
