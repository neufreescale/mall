package org.diwayou.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.diwayou.mq.message.MqHeaders;
import org.diwayou.mq.util.DestinationUtil;
import org.diwayou.mq.util.RocketmqConstants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author gaopeng 2021/2/3
 */
@Slf4j
public class MqRocketListenerContainer implements InitializingBean, DisposableBean, SmartLifecycle, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Map<String, InvocableHandlerMethod> handlerMethodMap;

    /**
     * The name of the DefaultRocketMQListenerContainer instance
     */
    private String name;

    private long suspendCurrentQueueTimeMillis = 1000;

    /**
     * Message consume retry strategy<br> -1,no retry,put into DLQ directly<br> 0,broker control retry frequency<br>
     * >0,client control retry frequency.
     */
    private int delayLevelWhenNextConsume = 0;

    private AccessChannel accessChannel = AccessChannel.LOCAL;

    private String topic;

    private int concurrency = 4;

    private DefaultMQPushConsumer consumer;

    private boolean running;

    private ConsumeMode consumeMode = ConsumeMode.CONCURRENTLY;

    private SelectorType selectorType = SelectorType.TAG;

    private String selectorExpression = "*";

    private MessageModel messageModel = MessageModel.CLUSTERING;

    private long consumeTimeout = 15L;

    public long getSuspendCurrentQueueTimeMillis() {
        return suspendCurrentQueueTimeMillis;
    }

    public void setSuspendCurrentQueueTimeMillis(long suspendCurrentQueueTimeMillis) {
        this.suspendCurrentQueueTimeMillis = suspendCurrentQueueTimeMillis;
    }

    public int getDelayLevelWhenNextConsume() {
        return delayLevelWhenNextConsume;
    }

    public void setDelayLevelWhenNextConsume(int delayLevelWhenNextConsume) {
        this.delayLevelWhenNextConsume = delayLevelWhenNextConsume;
    }

    public AccessChannel getAccessChannel() {
        return accessChannel;
    }

    public void setAccessChannel(AccessChannel accessChannel) {
        this.accessChannel = accessChannel;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getConcurrency() {
        return concurrency;
    }

    public MqRocketListenerContainer setConcurrency(int concurrency) {
        this.concurrency = concurrency;
        return this;
    }

    public ConsumeMode getConsumeMode() {
        return consumeMode;
    }

    public SelectorType getSelectorType() {
        return selectorType;
    }

    public void setSelectorExpression(String selectorExpression) {
        this.selectorExpression = selectorExpression;
    }

    public String getSelectorExpression() {
        return selectorExpression;
    }

    public MessageModel getMessageModel() {
        return messageModel;
    }

    public DefaultMQPushConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(DefaultMQPushConsumer consumer) {
        this.consumer = consumer;
    }

    public MqRocketListenerContainer setHandlerMethodMap(Map<String, InvocableHandlerMethod> handlerMethodMap) {
        this.handlerMethodMap = handlerMethodMap;
        return this;
    }

    @Override
    public void destroy() {
        this.setRunning(false);
        if (Objects.nonNull(consumer)) {
            consumer.shutdown();
        }
        log.info("container destroyed, {}", this.toString());
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        stop();
        callback.run();
    }

    @Override
    public void start() {
        if (this.isRunning()) {
            throw new IllegalStateException("container already running. " + this.toString());
        }

        try {
            consumer.start();
        } catch (MQClientException e) {
            throw new IllegalStateException("Failed to start RocketMQ push consumer", e);
        }
        this.setRunning(true);

        log.info("running container: {}", this.toString());
    }

    @Override
    public void stop() {
        if (this.isRunning()) {
            if (Objects.nonNull(consumer)) {
                consumer.shutdown();
            }
            setRunning(false);
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    private void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public int getPhase() {
        // Returning Integer.MAX_VALUE only suggests that
        // we will be the first bean to shutdown and last bean to start
        return Integer.MAX_VALUE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initRocketMQPushConsumer();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setName(String name) {
        this.name = name;
    }

    public class DefaultMessageListenerConcurrently implements MessageListenerConcurrently {

        @SuppressWarnings("unchecked")
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            for (MessageExt messageExt : msgs) {
                log.debug("received msg: {}", messageExt);
                try {
                    long now = System.currentTimeMillis();
                    handleMessage(messageExt);
                    long costTime = System.currentTimeMillis() - now;
                    log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
                } catch (Exception e) {
                    log.warn("consume message failed. messageId:{}, topic:{}, reconsumeTimes:{}", messageExt.getMsgId(), messageExt.getTopic(), messageExt.getReconsumeTimes(), e);
                    context.setDelayLevelWhenNextConsume(delayLevelWhenNextConsume);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

    public class DefaultMessageListenerOrderly implements MessageListenerOrderly {

        @SuppressWarnings("unchecked")
        @Override
        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
            for (MessageExt messageExt : msgs) {
                log.debug("received msg: {}", messageExt);
                try {
                    long now = System.currentTimeMillis();
                    handleMessage(messageExt);
                    long costTime = System.currentTimeMillis() - now;
                    log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
                } catch (Exception e) {
                    log.warn("consume message failed. messageId:{}, topic:{}, reconsumeTimes:{}", messageExt.getMsgId(), messageExt.getTopic(), messageExt.getReconsumeTimes(), e);
                    context.setSuspendCurrentQueueTimeMillis(suspendCurrentQueueTimeMillis);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
            }

            return ConsumeOrderlyStatus.SUCCESS;
        }
    }

    private void handleMessage(MessageExt messageExt) throws Exception {
        String destination = DestinationUtil.generate(messageExt.getTopic(), messageExt.getTags());
        InvocableHandlerMethod handlerMethod = handlerMethodMap.get(destination);
        if (handlerMethod == null) {
            handlerMethod = handlerMethodMap.get(topic);
        }

        if (handlerMethod == null) {
            log.warn("没找到处理方法topic={},tags={},msgId={}", messageExt.getTopic(), messageExt.getTags(), messageExt.getMsgId());
            return;
        }

        Message<?> message = MessageBuilder.withPayload(messageExt.getBody())
                .setHeader(MqHeaders.TOPIC, messageExt.getTopic())
                .setHeader(MqHeaders.TAG, messageExt.getTags())
                .build();
        handlerMethod.invoke(message);
    }

    private void initRocketMQPushConsumer() throws MQClientException {
        Assert.notNull(topic, "Property 'topic' is required");

        Environment environment = applicationContext.getEnvironment();

        RPCHook rpcHook = RocketMQUtil.getRPCHookByAkSk(environment, RocketMQMessageListener.ACCESS_KEY_PLACEHOLDER, RocketMQMessageListener.SECRET_KEY_PLACEHOLDER);
        boolean enableMsgTrace = true;
        String consumerGroup = environment.getProperty(RocketmqConstants.CONSUMER_GROUP);
        if (Objects.nonNull(rpcHook)) {
            consumer = new DefaultMQPushConsumer(consumerGroup, rpcHook, new AllocateMessageQueueAveragely(),
                    enableMsgTrace, this.applicationContext.getEnvironment().
                    resolveRequiredPlaceholders(RocketMQMessageListener.TRACE_TOPIC_PLACEHOLDER));
            consumer.setVipChannelEnabled(false);
        } else {
            log.debug("Access-key or secret-key not configure in " + this + ".");
            consumer = new DefaultMQPushConsumer(consumerGroup, enableMsgTrace,
                    this.applicationContext.getEnvironment().
                            resolveRequiredPlaceholders(RocketMQMessageListener.TRACE_TOPIC_PLACEHOLDER));
        }

        String nameServer = environment.resolveRequiredPlaceholders(RocketMQMessageListener.NAME_SERVER_PLACEHOLDER);

        consumer.setInstanceName(RocketMQUtil.getInstanceName(nameServer));

        consumer.setNamesrvAddr(nameServer);
        if (accessChannel != null) {
            consumer.setAccessChannel(accessChannel);
        }
        consumer.setConsumeThreadMax(concurrency);
        if (concurrency < consumer.getConsumeThreadMin()) {
            consumer.setConsumeThreadMin(concurrency);
        }
        consumer.setConsumeTimeout(consumeTimeout);

        switch (messageModel) {
            case BROADCASTING:
                consumer.setMessageModel(org.apache.rocketmq.common.protocol.heartbeat.MessageModel.BROADCASTING);
                break;
            case CLUSTERING:
                consumer.setMessageModel(org.apache.rocketmq.common.protocol.heartbeat.MessageModel.CLUSTERING);
                break;
            default:
                throw new IllegalArgumentException("Property 'messageModel' was wrong.");
        }

        switch (selectorType) {
            case TAG:
                consumer.subscribe(topic, selectorExpression);
                break;
            case SQL92:
                consumer.subscribe(topic, MessageSelector.bySql(selectorExpression));
                break;
            default:
                throw new IllegalArgumentException("Property 'selectorType' was wrong.");
        }

        switch (consumeMode) {
            case ORDERLY:
                consumer.setMessageListener(new DefaultMessageListenerOrderly());
                break;
            case CONCURRENTLY:
                consumer.setMessageListener(new DefaultMessageListenerConcurrently());
                break;
            default:
                throw new IllegalArgumentException("Property 'consumeMode' was wrong.");
        }
    }
}
