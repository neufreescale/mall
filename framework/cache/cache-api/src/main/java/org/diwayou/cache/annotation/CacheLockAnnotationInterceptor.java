package org.diwayou.cache.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.diwayou.cache.manager.LockManager;
import org.diwayou.core.exception.CustomException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.lang.reflect.Method;

/**
 * @author gaopeng 2021/2/1
 */
@Slf4j
public class CacheLockAnnotationInterceptor implements MethodInterceptor, BeanFactoryAware {

    private final CacheKeyEvaluator evaluator = new CacheKeyEvaluator();

    private LockManager lockManager;

    private BeanFactory beanFactory;

    public CacheLockAnnotationInterceptor(LockManager lockManager) {
        this.lockManager = lockManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object target = invocation.getThis();
        Object[] args = invocation.getArguments();

        CacheLock cache = method.getAnnotation(CacheLock.class);

        CacheKeyMeta keyMeta = new CacheKeyMeta(cache.key(), cache.express(), method, target.getClass());
        String key = evaluator.generateKey(keyMeta, target, args, this.beanFactory);

        return lockManager.executeWithLock(key, cache.ttl(), cache.unit(), () -> {
            try {
                return invocation.proceed();
            } catch (CustomException e) {
                throw e;
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
