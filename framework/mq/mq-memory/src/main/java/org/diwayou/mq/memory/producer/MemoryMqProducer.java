package org.diwayou.mq.memory.producer;

import org.diwayou.mq.memory.consumer.MqListenerRegistry;
import org.diwayou.mq.memory.message.MemoryMqMessage;
import org.diwayou.mq.message.MqHeaders;
import org.diwayou.mq.producer.MqProducer;
import org.diwayou.mq.producer.SendResult;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author gaopeng 2021/2/18
 */
public class MemoryMqProducer implements MqProducer {

    private ApplicationEventPublisher eventPublisher;

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public MemoryMqProducer(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public SendResult send(Message<?> message) {
        eventPublisher.publishEvent(new MemoryMqMessage(message));

        String topic = (String) message.getHeaders().get(MqHeaders.TOPIC);
        return new SendResult()
                .setMsgId("")
                .setTopic(topic);
    }

    @TransactionalEventListener(value = MemoryMqMessage.class, fallbackExecution = true)
    public void commonMessageHandle(MemoryMqMessage message) {
        executor.schedule(() -> MqListenerRegistry.I().handle(message.getMessage()), 1, TimeUnit.SECONDS);
    }
}
