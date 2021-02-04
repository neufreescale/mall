package org.diwayou.mq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.diwayou.mq.configuration.MqRocketListenerConfigurationSelector;
import org.diwayou.mq.producer.RocketMqProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author gaopeng 2021/2/3
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "rocketmq", value = "namespace")
@Import({MqRocketListenerConfigurationSelector.class})
public class MqRocketAutoConfiguration {

    @Bean
    public RocketMqProducer rocketMqProducer(RocketMQTemplate rocketMQTemplate) {
        return new RocketMqProducer(rocketMQTemplate);
    }
}
