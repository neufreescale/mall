package org.diwayou.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author gaopeng 2021/7/27
 */
@Slf4j
public class ServiceInfoPrinter implements ApplicationContextAware {

    private ConfigurableApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        Map<String, ServiceInfo> serviceInfoMap = applicationContext.getBeansOfType(ServiceInfo.class);
        StringBuilder info = new StringBuilder();
        info.append("启动服务包含:\n");
        serviceInfoMap.forEach((name, service) -> {
            info.append("服务Bean名: ");
            info.append(name);
            info.append('\n');

            info.append("服务名: ");
            info.append(service.name());
            info.append('\n');

            info.append("服务简介: ");
            info.append(service.info());
            info.append('\n');

        });

        log.info("{}", info);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
