package org.emall.order.fsm.handler;

import lombok.extern.slf4j.Slf4j;
import org.emall.order.model.command.OrderPaidCommand;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author gaopeng 2021/3/5
 */
@Component
@Slf4j
public class OrderPaidHandler {

    @EventListener(OrderPaidCommand.class)
    public void onCreate(OrderPaidCommand command) {
        log.info("订单支付成功{}", command);
    }
}
