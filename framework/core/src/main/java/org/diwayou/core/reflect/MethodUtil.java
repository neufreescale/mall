package org.diwayou.core.reflect;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author gaopeng 2021/2/4
 */
public class MethodUtil {

    public static Method checkProxy(Method methodArg, Object bean) {
        Method method = methodArg;
        if (AopUtils.isJdkDynamicProxy(bean)) {
            try {
                // Found a @Annotation method on the target class for this JDK proxy ->
                // is it also present on the proxy itself?
                method = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                Class<?>[] proxiedInterfaces = ((Advised) bean).getProxiedInterfaces();
                for (Class<?> iface : proxiedInterfaces) {
                    try {
                        method = iface.getMethod(method.getName(), method.getParameterTypes());
                        break;
                    } catch (@SuppressWarnings("unused") NoSuchMethodException noMethod) {
                        // NOSONAR
                    }
                }
            } catch (SecurityException ex) {
                ReflectionUtils.handleReflectionException(ex);
            } catch (NoSuchMethodException ex) {
                throw new IllegalStateException(String.format(
                        "@Annotation method '%s' found on bean target class '%s', " +
                                "but not found in any interface(s) for bean JDK proxy. Either " +
                                "pull the method up to an interface or switch to subclass (CGLIB) " +
                                "proxies by setting proxy-target-class/proxyTargetClass " +
                                "attribute to 'true'", method.getName(),
                        method.getDeclaringClass().getSimpleName()), ex);
            }
        }

        return method;
    }
}
