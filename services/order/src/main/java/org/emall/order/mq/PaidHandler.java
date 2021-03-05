package org.emall.order.mq;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.mq.annotation.MqListener;
import org.emall.order.manager.OrderManager;
import org.emall.pay.mq.PaidMessage;
import org.emall.pay.mq.PayMqConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gaopeng 2021/3/5
 */
@Component
@Slf4j
public class PaidHandler {

    @Autowired
    private OrderManager orderManager;

    @MqListener(topic = PayMqConstants.TOPIC, tag = PayMqConstants.TAG_PAID)
    public void paid(PaidMessage message) {
        log.info("接收到支付消息{}", message);
        orderManager.paid(message);
    }
}
