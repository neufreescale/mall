package org.diwayou.mq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.diwayou.mq.message.MqHeaders;
import org.diwayou.mq.transaction.MqCheckListenerRegistry;
import org.diwayou.mq.util.DestinationUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author gaopeng 2021/2/3
 */
@Slf4j
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

        if (TransactionSynchronizationManager.isSynchronizationActive() &&
                TransactionSynchronizationManager.isActualTransactionActive()) {
            if (MqCheckListenerRegistry.I().getHandler(topic, tag) == null) {
                log.warn("发送事务消息必须有MqCheckListener处理器topic={},tag={}", topic, tag);

                throw new RuntimeException("发送事务消息必须有MqCheckListener处理器");
            }

            try {
                return new TransactionProducer(rocketMQTemplate).send(destination, rocketMessage);
            } catch (MQClientException e) {
                throw new RuntimeException(e);
            }
        }

        org.apache.rocketmq.client.producer.SendResult result = rocketMQTemplate.syncSend(destination, rocketMessage);

        return new SendResult()
                .setTopic(result.getMessageQueue().getTopic())
                .setMsgId(result.getMsgId());
    }
}
