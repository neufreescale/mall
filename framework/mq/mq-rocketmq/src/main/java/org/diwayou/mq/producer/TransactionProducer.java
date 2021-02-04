package org.diwayou.mq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.rocketmq.client.Validators;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageAccessor;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author gaopeng 2021/2/4
 */
@Slf4j
public class TransactionProducer {

    private RocketMQTemplate rocketMQTemplate;

    private DefaultMQProducerImpl producer;

    private MessageConverter messageConverter;

    public TransactionProducer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;

        Field field = FieldUtils.getField(TransactionMQProducer.class, "defaultMQProducerImpl", true);

        this.producer = (DefaultMQProducerImpl) ReflectionUtils.getField(field, rocketMQTemplate.getProducer());
        if (this.producer == null) {
            throw new RuntimeException("不能发送事务消息");
        }

        this.messageConverter = rocketMQTemplate.getMessageConverter();
    }

    public SendResult send(String destination, org.springframework.messaging.Message<?> rocketMessage) throws MQClientException {
        Message msg = RocketMQUtil.convertToRocketMessage(messageConverter,
                StandardCharsets.UTF_8.toString(),
                destination, Objects.requireNonNull(messageConverter.toMessage(rocketMessage.getPayload(), rocketMessage.getHeaders())));
        // ignore DelayTimeLevel parameter
        if (msg.getDelayTimeLevel() != 0) {
            MessageAccessor.clearProperty(msg, MessageConst.PROPERTY_DELAY_TIME_LEVEL);
        }

        Validators.checkMessage(msg, producer.getDefaultMQProducer());

        org.apache.rocketmq.client.producer.SendResult sendResult;
        MessageAccessor.putProperty(msg, MessageConst.PROPERTY_TRANSACTION_PREPARED, "true");
        MessageAccessor.putProperty(msg, MessageConst.PROPERTY_PRODUCER_GROUP, producer.getDefaultMQProducer().getProducerGroup());
        try {
            sendResult = producer.send(msg);
        } catch (Exception e) {
            throw new MQClientException("send message Exception", e);
        }

        LocalTransactionState localTransactionState = LocalTransactionState.UNKNOW;
        switch (sendResult.getSendStatus()) {
            case FLUSH_DISK_TIMEOUT:
            case FLUSH_SLAVE_TIMEOUT:
            case SLAVE_NOT_AVAILABLE:
                localTransactionState = LocalTransactionState.ROLLBACK_MESSAGE;
                break;
            default:
                break;
        }

        final org.apache.rocketmq.client.producer.SendResult finalSendResult = sendResult;
        TransactionSynchronization transactionSynchronization = new TransactionSynchronizationAdapter() {
            @Override
            public void afterCompletion(int status) {
                LocalTransactionState localState;
                if (status == TransactionSynchronization.STATUS_ROLLED_BACK){
                    localState = LocalTransactionState.ROLLBACK_MESSAGE;
                } else if (status == TransactionSynchronization.STATUS_UNKNOWN) {
                    localState = LocalTransactionState.UNKNOW;
                } else {
                    localState = LocalTransactionState.COMMIT_MESSAGE;
                }

                try {
                    producer.endTransaction(finalSendResult, localState, null);
                } catch (Exception e) {
                    log.warn("local transaction execute " + localState + ", but end broker transaction failed", e);
                }
            }
        };

        TransactionSynchronizationManager.registerSynchronization(transactionSynchronization);

        if (localTransactionState.equals(LocalTransactionState.ROLLBACK_MESSAGE)) {
            throw new RuntimeException("rollback message because send mq fail");
        }

        return new SendResult()
                .setTopic(msg.getTopic())
                .setMsgId(sendResult.getMsgId());
    }
}
