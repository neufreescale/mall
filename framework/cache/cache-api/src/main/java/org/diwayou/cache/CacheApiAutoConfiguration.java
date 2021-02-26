package org.diwayou.cache;

import org.diwayou.cache.annotation.CacheAnnotationInterceptor;
import org.diwayou.cache.annotation.CacheLockAnnotationInterceptor;
import org.diwayou.cache.manager.LockManager;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * @author gaopeng 2021/2/1
 */
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class CacheApiAutoConfiguration {

    private static final String CACHE_ANNOTATION_EXPRESSION = "@annotation(org.diwayou.cache.annotation.Cache)";
    private static final String CACHE_LOCK_ANNOTATION_EXPRESSION = "@annotation(org.diwayou.cache.annotation.CacheLock)";

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AspectJExpressionPointcutAdvisor cacheAnnotationAdvisor(CacheAnnotationInterceptor interceptor) {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setAdvice(interceptor);
        advisor.setExpression(CACHE_ANNOTATION_EXPRESSION);

        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheAnnotationInterceptor cacheAnnotationInterceptor(KvCache kvCache) {
        return new CacheAnnotationInterceptor(kvCache);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LockManager lockManager(KvCache kvCache) {
        return new LockManager(kvCache);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AspectJExpressionPointcutAdvisor cacheLockAnnotationAdvisor(CacheLockAnnotationInterceptor interceptor) {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setAdvice(interceptor);
        advisor.setExpression(CACHE_LOCK_ANNOTATION_EXPRESSION);

        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheLockAnnotationInterceptor cacheLockAnnotationInterceptor(LockManager lockManager) {
        return new CacheLockAnnotationInterceptor(lockManager);
    }
}
