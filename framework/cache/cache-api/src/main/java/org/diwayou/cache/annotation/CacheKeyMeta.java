package org.diwayou.cache.annotation;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author gaopeng 2021/2/1
 */
public class CacheKeyMeta {

    public static final String SEP = "_";

    private final String key;

    private final String express;

    private final Method method;

    private final Class<?> targetClass;

    private final Method targetMethod;

    private final AnnotatedElementKey methodKey;

    public CacheKeyMeta(String key, String express, Method method, Class<?> targetClass) {
        this.method = BridgeMethodResolver.findBridgedMethod(method);
        this.targetClass = targetClass;
        this.targetMethod = (!Proxy.isProxyClass(targetClass) ?
                AopUtils.getMostSpecificMethod(method, targetClass) : this.method);
        this.methodKey = new AnnotatedElementKey(this.targetMethod, targetClass);

        if (StringUtils.hasText(key)) {
            this.key = key;
        } else {
            this.key = this.targetClass.getSimpleName() + SEP + this.targetMethod.getName();
        }
        this.express = express;

    }

    public String getKey() {
        return key;
    }

    public String getExpress() {
        return express;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public AnnotatedElementKey getMethodKey() {
        return methodKey;
    }
}
