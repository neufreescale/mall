package org.diwayou.mq.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.diwayou.mq.message.MqHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author gaopeng 2021/2/4
 */
@Slf4j
@RocketMQTransactionListener
public class MqTransactionListener implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object ctx) {
        if (!(ctx instanceof MqTransactionContext)) {
            log.warn("事务状态不合法 {}", msg);
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        MqTransactionContext context = (MqTransactionContext)ctx;

        try {
            context.getCallback().run();

            log.info("execute local msg {}", msg);

            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.warn("{}", msg, e);

            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String topic = msg.getHeaders().get(RocketMQUtil.toRocketHeaderKey(RocketMQHeaders.TOPIC), String.class);
        String tags = msg.getHeaders().get(RocketMQUtil.toRocketHeaderKey(RocketMQHeaders.TAGS), String.class);

        InvocableHandlerMethod handlerMethod = MqCheckListenerRegistry.I().getHandler(topic, tags);

        if (handlerMethod == null) {
            log.error("未找到checkListener {}", msg);
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        Message<?> message = MessageBuilder.withPayload(msg.getPayload())
                .setHeader(MqHeaders.TOPIC, topic)
                .setHeader(MqHeaders.TAG, tags)
                .build();

        try {
            TransactionCheckState checkState = (TransactionCheckState) handlerMethod.invoke(message);

            if (TransactionCheckState.COMMIT.equals(checkState)) {
                return RocketMQLocalTransactionState.COMMIT;
            } else if (TransactionCheckState.ROLLBACK.equals(checkState)) {
                return RocketMQLocalTransactionState.ROLLBACK;
            } else {
                return RocketMQLocalTransactionState.UNKNOWN;
            }
        } catch (Exception e) {
            log.error("", e);
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
