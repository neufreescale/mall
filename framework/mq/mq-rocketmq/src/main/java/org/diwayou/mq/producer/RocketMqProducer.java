package org.diwayou.mq.producer;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.diwayou.mq.message.MqHeaders;
import org.diwayou.mq.util.DestinationUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author gaopeng 2021/2/3
 */
public class RocketMqProducer implements MqProducer {

    private RocketMQTemplate rocketMQTemplate;

    public RocketMqProducer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @Override
    public SendResult send(Message<?> message) {
        MessageHeaders headers = message.getHeaders();
        String topic = (String) headers.get(MqHeaders.TOPIC);
        if (StringUtils.isBlank(topic)) {
            throw new IllegalArgumentException("topic参数不能为空");
        }
        String tag = (String) headers.get(MqHeaders.TAG);

        String destination = DestinationUtil.generate(topic, tag);

        Message<?> rocketMessage = MessageBuilder.withPayload(message.getPayload())
                .build();

        org.apache.rocketmq.client.producer.SendResult result = rocketMQTemplate.syncSend(destination, rocketMessage);

        return new SendResult()
                .setTopic(result.getMessageQueue().getTopic())
                .setMsgId(result.getMsgId());
    }
}
