package org.diwayou.cache;

import org.diwayou.cache.annotation.CacheAnnotationInterceptor;
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

    private static final String ANNOTATION_EXPRESSION = "@annotation(org.diwayou.cache.annotation.Cache)";

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AspectJExpressionPointcutAdvisor cacheAnnotationAdvisor(CacheAnnotationInterceptor interceptor) {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setAdvice(interceptor);
        advisor.setExpression(ANNOTATION_EXPRESSION);

        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheAnnotationInterceptor cacheAnnotationInterceptor(KvCache kvCache) {
        return new CacheAnnotationInterceptor(kvCache);
    }
}
