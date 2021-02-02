package org.diwayou.mq.converter;

import org.apache.kafka.clients.consumer.Consumer;
import org.diwayou.mq.message.MqHeaders;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.Map;

/**
 * @author gaopeng 2021/2/2
 */
public class MqRecordMessageConverter extends StringJsonMessageConverter {

    @Override
    public void commonHeaders(Acknowledgment acknowledgment, Consumer<?, ?> consumer, Map<String, Object> rawHeaders, Object theKey, Object topic, Object partition, Object offset, Object timestampType, Object timestamp) {
        super.commonHeaders(acknowledgment, consumer, rawHeaders, theKey, topic, partition, offset, timestampType, timestamp);

        rawHeaders.put(MqHeaders.TOPIC, topic);
    }
}
