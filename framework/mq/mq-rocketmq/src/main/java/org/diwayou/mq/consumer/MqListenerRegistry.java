package org.diwayou.mq.consumer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.Pair;
import org.diwayou.mq.annotation.MqListener;
import org.diwayou.mq.util.DestinationUtil;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author gaopeng 2021/2/3
 */
@Slf4j
public class MqListenerRegistry {

    private ConfigurableApplicationContext applicationContext;

    private Map<MqListener, InvocableHandlerMethod> registry = Maps.newConcurrentMap();

    private AtomicLong counter = new AtomicLong(0);

    public void register(MqListener mqListener, InvocableHandlerMethod method) {
        registry.put(mqListener, method);
    }

    public MqListenerRegistry setApplicationContext(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        return this;
    }

    public void registerAll() {
        Map<String, Map<String, Pair<MqListener, InvocableHandlerMethod>>> topicDestinationMap = Maps.newHashMap();
        registry.forEach(((mqListener, method) -> {
            String topic = mqListener.topic();
            String tag = mqListener.tag();

            Map<String, Pair<MqListener, InvocableHandlerMethod>> destinationMethodMap = topicDestinationMap.computeIfAbsent(topic, k -> Maps.newHashMap());

            String destination = DestinationUtil.generate(topic, tag);
            Pair<MqListener, InvocableHandlerMethod> old = destinationMethodMap.put(destination, new Pair<>(mqListener, method));
            if (old != null) {
                throw new RuntimeException(String.format("不能重复监听topic=%s,tag=%s", topic, tag));
            }
        }));

        topicDestinationMap.forEach((topic, destinationMethodMap) -> {
            if (destinationMethodMap.size() > 1 && destinationMethodMap.get(topic) != null) {
                throw new RuntimeException(String.format("当使用tag监听的时候，不能只监听topic，防止重复监听topic=%s", topic));
            }

            List<String> tags = Lists.newArrayList();
            int maxConcurrency = 1;
            Map<String, InvocableHandlerMethod> handlerMethodMap = Maps.newHashMap();
            for (Map.Entry<String, Pair<MqListener, InvocableHandlerMethod>> entry : destinationMethodMap.entrySet()) {
                Pair<MqListener, InvocableHandlerMethod> pair = entry.getValue();
                MqListener mqListener = pair.getObject1();
                if (StringUtils.isNotBlank(mqListener.tag())) {
                    tags.add(mqListener.tag());
                }

                int concurrency = Integer.parseInt(mqListener.concurrency());
                if (concurrency > maxConcurrency) {
                    maxConcurrency = concurrency;
                }

                handlerMethodMap.put(entry.getKey(), pair.getObject2());
            }

            createListenerContainer(topic, tags, maxConcurrency, handlerMethodMap);
        });
    }

    private void createListenerContainer(String topic, List<String> tags, int maxConcurrency, Map<String, InvocableHandlerMethod> handlerMethodMap) {
        String containerBeanName = String.format("%s_%s", MqRocketListenerContainer.class.getName(), counter.incrementAndGet());
        GenericApplicationContext genericApplicationContext = (GenericApplicationContext) applicationContext;

        genericApplicationContext.registerBean(containerBeanName, MqRocketListenerContainer.class,
                () -> createMqRocketListenerContainer(containerBeanName, topic, tags, maxConcurrency, handlerMethodMap));
        MqRocketListenerContainer container = genericApplicationContext.getBean(containerBeanName,
                MqRocketListenerContainer.class);
        if (!container.isRunning()) {
            try {
                container.start();
            } catch (Exception e) {
                log.error("Started container failed. {}", container, e);
                throw new RuntimeException(e);
            }
        }
    }

    private MqRocketListenerContainer createMqRocketListenerContainer(String containerBeanName, String topic,
                                                                      List<String> tags,
                                                                      int maxConcurrency,
                                                                      Map<String, InvocableHandlerMethod> handlerMethodMap) {
        MqRocketListenerContainer container = new MqRocketListenerContainer();

        container.setTopic(topic);
        container.setName(containerBeanName);
        if (CollectionUtils.isNotEmpty(tags)) {
            container.setSelectorExpression(StringUtils.join(tags, "||"));
        }
        container.setConcurrency(maxConcurrency);
        container.setHandlerMethodMap(handlerMethodMap);

        log.info("Create mq container topic={},tag={},concurrency={}", container.getTopic(), container.getSelectorExpression(), container.getConcurrency());

        return container;
    }
}
