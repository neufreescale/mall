package org.emall.mq.common;

import org.diwayou.mq.annotation.MqListener;

import java.lang.reflect.Method;

/**
 * @author gaopeng 2021/2/4
 */
@FunctionalInterface
public interface ListenerHandler {

    void process(MqListener mqListener, Method method, Object bean, String beanName);
}
