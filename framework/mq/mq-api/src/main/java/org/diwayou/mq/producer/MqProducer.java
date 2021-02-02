package org.diwayou.mq.producer;

import org.springframework.messaging.Message;

/**
 * @author gaopeng 2021/2/2
 */
public interface MqProducer {

    SendResult send(Message<?> message);
}
