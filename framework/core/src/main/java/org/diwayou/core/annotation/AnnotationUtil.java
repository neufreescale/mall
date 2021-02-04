package org.diwayou.core.annotation;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.core.reflect.MethodUtil;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author gaopeng 2021/2/4
 */
@Slf4j
public class AnnotationUtil {

    public static <T extends Annotation> void processBean(Object bean, String beanName, Class<T> annotationClass, ListenerHandler<T> listenerHandler) {
        Class<?> targetClass = AopUtils.getTargetClass(bean);

        Map<Method, Set<T>> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
                (MethodIntrospector.MetadataLookup<Set<T>>) method -> {
                    Set<T> listenerMethods = findListenerAnnotations(method, annotationClass);
                    return (!listenerMethods.isEmpty() ? listenerMethods : null);
                });

        if (annotatedMethods.isEmpty()) {
            log.trace("No {} annotations found on bean type: {}", annotationClass.getSimpleName(), bean.getClass());

            return;
        }

        for (Map.Entry<Method, Set<T>> entry : annotatedMethods.entrySet()) {
            Method method = entry.getKey();
            Method methodToUse = MethodUtil.checkProxy(method, bean);

            for (T listener : entry.getValue()) {
                listenerHandler.process(listener, methodToUse, bean, beanName);
            }
        }
        log.debug("{} {} methods processed on bean '{}': {}", annotatedMethods.size(), annotationClass.getSimpleName(),
                beanName, annotatedMethods);
    }

    private static <T extends Annotation> Set<T> findListenerAnnotations(Method method, Class<T> annotationClass) {
        Set<T> listeners = new HashSet<>();
        T ann = AnnotatedElementUtils.findMergedAnnotation(method, annotationClass);
        if (ann != null) {
            listeners.add(ann);
        }

        return listeners;
    }
}
