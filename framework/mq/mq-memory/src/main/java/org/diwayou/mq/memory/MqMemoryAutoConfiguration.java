package org.diwayou.mq.memory;

import org.diwayou.mq.memory.configuration.MqMemoryListenerConfigurationSelector;
import org.diwayou.mq.memory.producer.MemoryMqProducer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author gaopeng 2021/2/18
 */
@Configuration(proxyBeanMethods = false)
@Import({MqMemoryListenerConfigurationSelector.class})
public class MqMemoryAutoConfiguration {

    @Bean
    public MemoryMqProducer memoryMqProducer(ApplicationEventPublisher eventPublisher) {
        return new MemoryMqProducer(eventPublisher);
    }
}
