package org.diwayou.mq.transaction;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.Pair;
import org.diwayou.mq.util.DestinationUtil;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.util.List;
import java.util.Map;

/**
 * @author gaopeng 2021/2/4
 */
@Slf4j
public class MqCheckListenerRegistry {

    private static MqCheckListenerRegistry instance = new MqCheckListenerRegistry();

    private Map<MqCheckListener, InvocableHandlerMethod> registry = Maps.newConcurrentMap();

    private Map<String, InvocableHandlerMethod> handlerMethodMap = Maps.newHashMap();

    public static MqCheckListenerRegistry I() {
        return instance;
    }

    private MqCheckListenerRegistry() {}

    public void register(MqCheckListener mqListener, InvocableHandlerMethod method) {
        registry.put(mqListener, method);
    }

    public InvocableHandlerMethod getHandler(String topic, String tag) {
        String destination = DestinationUtil.generate(topic, tag);
        InvocableHandlerMethod handlerMethod = handlerMethodMap.get(destination);
        if (handlerMethod == null) {
            handlerMethod = handlerMethodMap.get(topic);
        }

        return handlerMethod;
    }

    public void registerAll() {
        Map<String, Map<String, Pair<MqCheckListener, InvocableHandlerMethod>>> topicDestinationMap = Maps.newHashMap();
        registry.forEach(((mqListener, method) -> {
            String topic = mqListener.topic();
            String tag = mqListener.tag();

            Map<String, Pair<MqCheckListener, InvocableHandlerMethod>> destinationMethodMap = topicDestinationMap.computeIfAbsent(topic, k -> Maps.newHashMap());

            String destination = DestinationUtil.generate(topic, tag);
            Pair<MqCheckListener, InvocableHandlerMethod> old = destinationMethodMap.put(destination, new Pair<>(mqListener, method));
            if (old != null) {
                throw new RuntimeException(String.format("不能重复监听topic=%s,tag=%s", topic, tag));
            }
        }));

        Map<String, InvocableHandlerMethod> handlerMethodMap = Maps.newHashMap();
        topicDestinationMap.forEach((topic, destinationMethodMap) -> {
            if (destinationMethodMap.size() > 1 && destinationMethodMap.get(topic) != null) {
                throw new RuntimeException(String.format("当使用tag监听的时候，不能只监听topic，防止重复监听topic=%s", topic));
            }

            List<String> tags = Lists.newArrayList();

            for (Map.Entry<String, Pair<MqCheckListener, InvocableHandlerMethod>> entry : destinationMethodMap.entrySet()) {
                Pair<MqCheckListener, InvocableHandlerMethod> pair = entry.getValue();
                MqCheckListener mqListener = pair.getObject1();
                if (StringUtils.isNotBlank(mqListener.tag())) {
                    tags.add(mqListener.tag());
                }

                handlerMethodMap.put(entry.getKey(), pair.getObject2());
            }
        });

        this.handlerMethodMap = handlerMethodMap;
    }
}
