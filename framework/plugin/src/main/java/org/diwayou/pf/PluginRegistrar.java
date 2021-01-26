package org.diwayou.pf;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.diwayou.pf.annotation.EnablePlugin;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author gaopeng 2021/1/15
 */
@Slf4j
public class PluginRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(EnablePlugin.class.getName()));
        if (attributes == null) {
            throw new RuntimeException("未配置EnablePlugin");
        }

        String pluginsRoot = attributes.getString("pluginsRoot");
        String systemVersion = attributes.getString("systemVersion");

        PluginConfiguration configuration = new PluginConfiguration(systemVersion);

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(SpringPluginManager.class);
        if (StringUtils.isNotBlank(pluginsRoot)) {
            builder.addConstructorArgValue(pluginsRoot);
        }
        builder.addConstructorArgValue(configuration);
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
    }
}
