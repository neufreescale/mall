package org.diwayou.mq.producer;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.diwayou.mq.message.MqHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.ExecutionException;

/**
 * @author gaopeng 2021/2/2
 */
public class KafkaMqProducer implements MqProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaMqProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public SendResult send(Message<?> message) {
        MessageHeaders headers = message.getHeaders();
        String topic = (String) headers.get(MqHeaders.TOPIC);
        if (StringUtils.isBlank(topic)) {
            throw new IllegalArgumentException("topic参数不能为空");
        }

        Message<?> kafkaMessage = MessageBuilder.withPayload(message.getPayload())
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        RecordMetadata metadata;
        try {
            metadata = kafkaTemplate.send(kafkaMessage).get().getRecordMetadata();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return new SendResult()
                .setTopic(metadata.topic())
                .setMsgId(String.valueOf(metadata.offset()));
    }
}
