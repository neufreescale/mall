package org.diwayou.mq.memory.consumer;

import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.diwayou.mq.annotation.MqListener;
import org.diwayou.mq.message.MqHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.util.Map;

/**
 * @author gaopeng 2021/2/18
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MqListenerRegistry {

    private static final MqListenerRegistry instance = new MqListenerRegistry();

    public static MqListenerRegistry I() {
        return instance;
    }

    private Map<MqListener, InvocableHandlerMethod> registry = Maps.newConcurrentMap();

    private Map<String, InvocableHandlerMethod> handlerMethodMap = Maps.newHashMap();

    public void register(MqListener mqListener, InvocableHandlerMethod method) {
        registry.put(mqListener, method);
    }

    public void registerAll() {
        registry.forEach(((mqListener, method) -> {
            String topic = mqListener.topic();
            String tag = mqListener.tag();

            String key = genKey(topic, tag);
            InvocableHandlerMethod old = handlerMethodMap.put(key, method);
            if (old != null) {
                throw new IllegalStateException(String.format("不能重复订阅topic=%s,tag=%s", topic, tag));
            }
        }));
    }

    public void handle(Message<?> message) {
        MessageHeaders headers = message.getHeaders();
        String topic = (String) headers.get(MqHeaders.TOPIC);
        String tag = (String) headers.get(MqHeaders.TAG);

        String key = genKey(topic, tag);
        InvocableHandlerMethod handlerMethod = handlerMethodMap.get(key);
        if (handlerMethod == null) {
            handlerMethod = handlerMethodMap.get(topic);
        }

        if (handlerMethod == null) {
            log.warn("不能处理消息topic={},tag={}", topic, tag);
            return;
        }

        try {
            handlerMethod.invoke(message);
        } catch (Exception e) {
            log.error("处理消息失败", e);
        }
    }

    private static String genKey(String topic, String tag) {
        if (StringUtils.isBlank(tag)) {
            return topic;
        }

        return topic + ":" + tag;
    }
}
