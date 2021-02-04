package org.diwayou.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author gaopeng 2021/2/4
 */
@FunctionalInterface
public interface ListenerHandler<T extends Annotation> {

    void process(T mqListener, Method method, Object bean, String beanName);
}
