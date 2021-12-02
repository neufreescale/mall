package org.diwayou.jdbc;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.diwayou.jdbc.config.ConfigManager;
import org.diwayou.jdbc.datasource.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author gaopeng 2021/11/16
 */
@Slf4j
public class DataSourceRWRouter {

    private Set<TransactionAttributeSource> transactionAttributeSources = Sets.newHashSet();

    public DataSourceRWRouter() {
    }

    public Object determineReadOrWriteDB(ProceedingJoinPoint pjp) throws Throwable {
        if (!ConfigManager.isNeedReadWriteSeparate()) {
            return pjp.proceed();
        }

        // 只能设置一次
        if (HintManager.isInstantiated()) {
            return pjp.proceed();
        }

        if (routeMaster(pjp)) {
            try (HintManager hintManager = HintManager.getInstance()) {
                hintManager.setDataSourceIndex(HintManager.MASTER_INDEX);

                return pjp.proceed();
            }
        }

        return pjp.proceed();
    }

    private boolean routeMaster(ProceedingJoinPoint pjp) {
        if (TransactionSynchronizationManager.isActualTransactionActive() && !TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return true;
        }

        String methodName = pjp.getSignature().getName();
        if (StringUtils.startsWith(methodName, "master")) {
            return true;
        }

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Class<?> clazz = pjp.getTarget().getClass();

        for (TransactionAttributeSource transactionAttributeSource : transactionAttributeSources) {
            TransactionAttribute transactionAttribute = transactionAttributeSource.getTransactionAttribute(method, clazz);

            if (transactionAttribute != null && !transactionAttribute.isReadOnly()) {
                return true;
            }
        }

        return false;
    }

    @Autowired
    public void setTransactionAttributeSources(List<TransactionInterceptor> transactionInterceptors) {
        if (transactionInterceptors.isEmpty()) {
            throw new IllegalStateException("没有找到事务拦截器，不能使用thindb，请正确配置事务拦截器");
        }

        transactionAttributeSources = transactionInterceptors.stream()
                .map(TransactionAspectSupport::getTransactionAttributeSource)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        log.info("找到事务规则{}个，{}", transactionAttributeSources.size(), transactionAttributeSources);
    }
}
