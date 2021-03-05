package org.emall.pay.manager;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.mq.message.MqHeaders;
import org.diwayou.mq.producer.MqProducer;
import org.diwayou.mq.producer.SendResult;
import org.emall.pay.mq.PaidMessage;
import org.emall.pay.mq.PayMqConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author gaopeng 2021/3/5
 */
@Component
@Slf4j
public class PayManager {

    @Autowired
    private MqProducer mqProducer;

    public void create() {
        Message<?> paidMessage = MessageBuilder.withPayload(new PaidMessage())
                .setHeader(MqHeaders.TOPIC, PayMqConstants.TOPIC)
                .setHeader(MqHeaders.TAG, PayMqConstants.TAG_PAID)
                .build();
        SendResult result = mqProducer.send(paidMessage);

        log.info("支付成功 result={}", result);
    }
}
