package org.diwayou.mq;

import org.diwayou.mq.configuration.MqKafkaListenerConfigurationSelector;
import org.diwayou.mq.converter.MqRecordMessageConverter;
import org.diwayou.mq.producer.KafkaMqProducer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.RecordMessageConverter;

/**
 * @author gaopeng 2021/2/2
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "kafka", value = "namespace")
@Import(MqKafkaListenerConfigurationSelector.class)
@AutoConfigureBefore(KafkaAutoConfiguration.class)
public class MqKafkaAutoConfiguration {

    @Bean
    public KafkaMqProducer kafkaMqProducer(KafkaTemplate kafkaTemplate) {
        return new KafkaMqProducer(kafkaTemplate);
    }

    @Bean
    public RecordMessageConverter recordMessageConverter() {
        return new MqRecordMessageConverter();
    }
}
