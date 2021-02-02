package org.diwayou.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.mq.annotation.MqListener;
import org.emall.mq.common.MqListenerUtil;
import org.springframework.beans.BeansException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gaopeng 2021/2/2
 */
@Slf4j
public class MqKafkaListenerAnnotationBeanPostProcessor<K, V> extends KafkaListenerAnnotationBeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        MqListenerUtil.processMqListenerBean(bean, beanName, this::processMqListener);

        return super.postProcessAfterInitialization(bean, beanName);
    }

    protected void processMqListener(MqListener mqListener, Method method, Object bean, String beanName) {
        MethodKafkaListenerEndpoint<K, V> endpoint = new MethodKafkaListenerEndpoint<>();
        endpoint.setMethod(method);

        KafkaListener kafkaListener = createFromMqListener(mqListener);

        processListener(endpoint, kafkaListener, bean, method, beanName);
    }

    private KafkaListener createFromMqListener(MqListener mqListener) {
        Map<String, Object> annotationParameters = new HashMap<>();
        annotationParameters.put("id", "");
        annotationParameters.put("containerFactory", "");
        annotationParameters.put("topics", new String[]{mqListener.topic()});
        annotationParameters.put("topicPattern", "");
        annotationParameters.put("topicPartitions", new TopicPartition[]{});
        annotationParameters.put("containerGroup", "");
        annotationParameters.put("errorHandler", "");
        annotationParameters.put("groupId", "");
        annotationParameters.put("idIsGroup", true);
        annotationParameters.put("clientIdPrefix", "");
        annotationParameters.put("beanRef", "__listener");
        annotationParameters.put("concurrency", mqListener.concurrency());
        annotationParameters.put("autoStartup", "");
        annotationParameters.put("properties", new String[]{});
        annotationParameters.put("splitIterables", true);

        KafkaListener kafkaListener = AnnotationUtils.synthesizeAnnotation(annotationParameters, KafkaListener.class, null);

        return kafkaListener;
    }


}
