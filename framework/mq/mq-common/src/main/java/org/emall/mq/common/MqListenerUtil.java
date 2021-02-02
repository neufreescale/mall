package org.emall.mq.common;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.mq.annotation.MqListener;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author gaopeng 2021/2/4
 */
@Slf4j
public class MqListenerUtil {

    public static void processMqListenerBean(Object bean, String beanName, ListenerHandler listenerHandler) {
        Class<?> targetClass = AopUtils.getTargetClass(bean);

        Map<Method, Set<MqListener>> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
                (MethodIntrospector.MetadataLookup<Set<MqListener>>) method -> {
                    Set<MqListener> listenerMethods = findListenerAnnotations(method);
                    return (!listenerMethods.isEmpty() ? listenerMethods : null);
                });

        if (annotatedMethods.isEmpty()) {
            log.trace("No @MqListener annotations found on bean type: {}", bean.getClass());

            return;
        }

        for (Map.Entry<Method, Set<MqListener>> entry : annotatedMethods.entrySet()) {
            Method method = entry.getKey();
            Method methodToUse = MethodUtil.checkProxy(method, bean);

            for (MqListener listener : entry.getValue()) {
                listenerHandler.process(listener, methodToUse, bean, beanName);
            }
        }
        log.debug("{} @MqListener methods processed on bean '{}': {}", annotatedMethods.size(), beanName, annotatedMethods);
    }

    private static Set<MqListener> findListenerAnnotations(Method method) {
        Set<MqListener> listeners = new HashSet<>();
        MqListener ann = AnnotatedElementUtils.findMergedAnnotation(method, MqListener.class);
        if (ann != null) {
            listeners.add(ann);
        }

        return listeners;
    }
}
