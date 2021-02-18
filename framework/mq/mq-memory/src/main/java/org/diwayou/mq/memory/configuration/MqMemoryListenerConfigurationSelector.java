package org.diwayou.mq.memory.configuration;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author gaopeng 2021/2/18
 */
public class MqMemoryListenerConfigurationSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MqMemoryBootstrapConfiguration.class.getName()};
    }
}
