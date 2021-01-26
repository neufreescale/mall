package org.diwayou.fsm;

import org.diwayou.fsm.annotation.EnableStateMachine;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author gaopeng 2021/1/19
 */
public class StateMachineRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableStateMachine.class.getName()));
        if (attributes == null) {
            throw new IllegalArgumentException("未配置EnableFlowEngine");
        }

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(StateMachineConfiguration.class);
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
    }
}
