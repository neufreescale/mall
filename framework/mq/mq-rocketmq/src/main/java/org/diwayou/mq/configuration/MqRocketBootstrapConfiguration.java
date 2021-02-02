package org.diwayou.mq.configuration;

import org.diwayou.mq.consumer.MqRocketListenerAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author gaopeng 2021/2/2
 */
@Configuration(proxyBeanMethods = false)
public class MqRocketBootstrapConfiguration implements ImportBeanDefinitionRegistrar {

    public static final String ROCKET_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME =
            "org.diwayou.mq.consumer.MqRocketListenerAnnotationBeanPostProcessor";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(ROCKET_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME)) {

            registry.registerBeanDefinition(ROCKET_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME,
                    new RootBeanDefinition(MqRocketListenerAnnotationBeanPostProcessor.class));
        }
    }
}
