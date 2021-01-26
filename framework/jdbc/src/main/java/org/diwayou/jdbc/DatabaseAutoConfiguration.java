package org.diwayou.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.config.IConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author gaopeng 2021/1/20
 */
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@ConditionalOnProperty(prefix = "database", value = "namespace")
@EnableTransactionManagement
@Import({DatabaseConfiguration.class})
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@Slf4j
public class DatabaseAutoConfiguration implements EnvironmentAware {

    @Autowired
    private Environment environment;

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DatabaseFactory databaseFactory(IConfig iConfig) {
        return new DatabaseFactory(iConfig);
    }

    @Bean
    @ConditionalOnMissingBean(name = "dataSource")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DataSource dataSource(DatabaseFactory databaseFactory) {
        String ns = environment.getProperty("database.namespace");
        log.info("auto config database with namespace {}", ns);

        try {
            return databaseFactory.create(ns);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}

