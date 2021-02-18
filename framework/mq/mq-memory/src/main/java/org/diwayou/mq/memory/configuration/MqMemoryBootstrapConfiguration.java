package org.diwayou.mq.memory.configuration;

import org.diwayou.mq.memory.consumer.MqMemoryListenerAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author gaopeng 2021/2/18
 */
@Configuration(proxyBeanMethods = false)
public class MqMemoryBootstrapConfiguration implements ImportBeanDefinitionRegistrar {

    public static final String MEMORY_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME =
            "org.diwayou.mq.memory.consumer.MqMemoryListenerAnnotationBeanPostProcessor";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(MEMORY_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME)) {

            registry.registerBeanDefinition(MEMORY_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME,
                    new RootBeanDefinition(MqMemoryListenerAnnotationBeanPostProcessor.class));
        }
    }
}
