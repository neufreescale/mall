package org.diwayou.cache.annotation;

import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.diwayou.cache.KvCache;
import org.diwayou.core.json.Json;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.lang.reflect.Method;

/**
 * @author gaopeng 2021/2/1
 */
@Slf4j
public class CacheAnnotationInterceptor implements MethodInterceptor, BeanFactoryAware {

    private final CacheKeyEvaluator evaluator = new CacheKeyEvaluator();

    private KvCache kvCache;

    private BeanFactory beanFactory;

    public CacheAnnotationInterceptor(KvCache kvCache) {
        this.kvCache = kvCache;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object target = invocation.getThis();
        Object[] args = invocation.getArguments();

        Cache cache = method.getAnnotation(Cache.class);

        CacheKeyMeta keyMeta = new CacheKeyMeta(cache.key(), cache.express(), method, target.getClass());
        String key = evaluator.generateKey(keyMeta, target, args, this.beanFactory);

        String value = kvCache.get(key);
        if (value != null) {
            return Json.I().fromJson(value, TypeFactory.defaultInstance().constructType(method.getGenericReturnType()));
        }

        Object result = invocation.proceed();

        kvCache.set(key, Json.I().toJson(result), cache.ttl());

        return result;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
