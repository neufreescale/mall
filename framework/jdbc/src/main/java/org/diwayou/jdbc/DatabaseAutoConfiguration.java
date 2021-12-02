package org.diwayou.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.jdbc.datasource.EmallDataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author gaopeng 2021/1/20
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "database", value = "namespace")
@EnableTransactionManagement
@Import({DatabaseConfiguration.class})
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@Slf4j
public class DatabaseAutoConfiguration implements EnvironmentAware {

    @Autowired
    private Environment environment;

    @Bean
    @ConditionalOnMissingBean(name = "dataSource")
    public DataSource dataSource() {
        String ns = environment.getProperty("database.namespace");
        log.info("auto config database with namespace {}", ns);

        try {
            return new EmallDataSource(ns);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    @ConditionalOnMissingBean(PlatformTransactionManager.class)
    public DataSourceTransactionManager transactionManager(DataSource dataSource,
                                                    ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
        return transactionManager;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}

