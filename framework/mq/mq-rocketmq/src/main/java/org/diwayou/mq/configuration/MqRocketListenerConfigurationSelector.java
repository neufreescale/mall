package org.diwayou.mq.configuration;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author gaopeng 2021/2/2
 */
public class MqRocketListenerConfigurationSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MqRocketBootstrapConfiguration.class.getName()};
    }
}
