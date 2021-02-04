package org.diwayou.job;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ElasticJob;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gaopeng 2021/2/4
 */
@Slf4j
public class SimpleJobConfiguration implements ApplicationContextAware, SmartInitializingSingleton, DisposableBean {

    private ConfigurableApplicationContext applicationContext;

    private CoordinatorRegistryCenter coordinatorRegistryCenter;

    private Map<String, ScheduleJobBootstrap> jobs = Maps.newHashMapWithExpectedSize(6);

    public SimpleJobConfiguration(CoordinatorRegistryCenter coordinatorRegistryCenter) {
        this.coordinatorRegistryCenter = coordinatorRegistryCenter;
    }

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(Job.class).entrySet().stream()
                .filter(entry -> !ScopedProxyUtils.isScopedTarget(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        beans.forEach(this::registerJob);
    }

    private void registerJob(String beanName, Object bean) {
        Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);
        if (!SimpleJob.class.isAssignableFrom(bean.getClass())) {
            throw new IllegalStateException(clazz + " is not instance of " + SimpleJob.class.getName());
        }

        Job jobAnnotation = AnnotationUtils.findAnnotation(clazz, Job.class);

        JobConfiguration jobConfiguration = JobConfiguration.newBuilder(jobAnnotation.name(), jobAnnotation.shardingTotalCount())
                .cron(jobAnnotation.cron())
                .shardingItemParameters(jobAnnotation.shardingItemParameters())
                .jobParameter(jobAnnotation.jobParameter())
                .failover(jobAnnotation.failover())
                .misfire(jobAnnotation.misfire())
                .overwrite(jobAnnotation.overwrite())
                .build();
        ElasticJob job = (ElasticJob) bean;
        ScheduleJobBootstrap jobBootstrap = new ScheduleJobBootstrap(coordinatorRegistryCenter, job, jobConfiguration);

        jobs.put(jobAnnotation.name(), jobBootstrap);

        jobBootstrap.schedule();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        jobs.values().forEach(ScheduleJobBootstrap::shutdown);
    }
}
