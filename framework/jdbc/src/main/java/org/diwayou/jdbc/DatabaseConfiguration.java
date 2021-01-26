package org.diwayou.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.aop.aspectj.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.util.Collections;

/**
 * @author gaopeng 2021/1/25
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class DatabaseConfiguration implements EnvironmentAware {

    private static final String DAO_XML_PATH = "classpath:mybatis/**/*Mapper.xml";

    private static final String[] DAO_BASE_PACKAGE = {"org.emall.**.dao"};

    private static final String TRANSACTION_POINTCUT_EXPRESSION = "execution(* org.emall..*.service..*.*(..))";

    private static final String[] TRANSACTION_REQUIRED_NAME_MAP = {"add*", "edit*", "remove*", "insert*", "save*", "update*",
            "modify*", "delete*", "do*", "process*", "on*", "create*"};

    private Environment environment;

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        sqlSessionFactoryBean.setDataSource(dataSource);
        try {
            String mapperPath = environment.getProperty("mapper.path", DAO_XML_PATH);

            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperPath);
            sqlSessionFactoryBean.setMapperLocations(resources);
        } catch (FileNotFoundException e) {
            log.warn(e.getMessage());
        }

        return sqlSessionFactoryBean;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public MapperScannerConfigurer mapperScannerConfigurer() {
        String scanPackage = environment.getProperty("daoScanPackage", StringUtils.join(DAO_BASE_PACKAGE, ","));

        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage(scanPackage);
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");

        return configurer;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public NameMatchTransactionAttributeSource nameMatchTransactionAttributeSource() {
        /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);

        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        NameMatchTransactionAttributeSource attributeSource = new NameMatchTransactionAttributeSource();
        for (String pattern : TRANSACTION_REQUIRED_NAME_MAP) {
            attributeSource.addTransactionalMethod(pattern, requiredTx);
        }
        attributeSource.addTransactionalMethod("*", readOnlyTx);

        return attributeSource;
    }

    private String getTransactionPointcutExpression() {
        return environment.getProperty("transactionScanExpr", TRANSACTION_POINTCUT_EXPRESSION);
    }

    /**
     * 事务拦截器
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AspectJExpressionPointcutAdvisor transactionAdvisor(DataSource dataSource) {
        TransactionManager transactionManager = new DataSourceTransactionManager(dataSource);

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setAdvice(new TransactionInterceptor(transactionManager, nameMatchTransactionAttributeSource()));
        advisor.setExpression(getTransactionPointcutExpression());

        return advisor;
    }

    /**
     * 主库、从库选择AOP
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AspectJExpressionPointcutAdvisor determineReadOrWriteDBAdvisor() throws NoSuchMethodException {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(getTransactionPointcutExpression());
        advisor.setOrder(Integer.MIN_VALUE);

        AspectInstanceFactory aspectInstanceFactory = new SingletonAspectInstanceFactory(new ShardingsphereRWRouter());
        AspectJAroundAdvice aroundAdvice = new AspectJAroundAdvice(ShardingsphereRWRouter.class.getMethod("determineReadOrWriteDB", ProceedingJoinPoint.class),
                (AspectJExpressionPointcut) advisor.getPointcut(),
                aspectInstanceFactory);

        advisor.setAdvice(aroundAdvice);

        return advisor;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
