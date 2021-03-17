package org.emall.pay.manager;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.mq.message.MqHeaders;
import org.diwayou.mq.producer.MqProducer;
import org.diwayou.mq.producer.SendResult;
import org.emall.pay.mq.PaidMessage;
import org.emall.pay.mq.PayMqConstants;
import org.emall.wechat.pay.PayClient;
import org.emall.wechat.pay.request.UnifiedPayRequest;
import org.emall.wechat.pay.response.UnifiedPayResponse;
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

    @Autowired
    private PayClient payClient;

    public void create() {
        UnifiedPayRequest request = new UnifiedPayRequest();
        request.setOutTradeNo("123");
        UnifiedPayResponse res = payClient.unifiedOrder(request);
        log.info("{}", res);
        
        Message<?> paidMessage = MessageBuilder.withPayload(new PaidMessage())
                .setHeader(MqHeaders.TOPIC, PayMqConstants.TOPIC)
                .setHeader(MqHeaders.TAG, PayMqConstants.TAG_PAID)
                .build();
        SendResult result = mqProducer.send(paidMessage);

        log.info("支付成功 result={}", result);
    }
}
