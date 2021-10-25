package org.emall.order.fsm.handler;

import lombok.extern.slf4j.Slf4j;
import org.emall.order.model.command.OrderCreateCommand;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author gaopeng 2021/3/5
 */
@Component
@Slf4j
public class OrderCreateHandler {

    @EventListener(OrderCreateCommand.class)
    public void onCreate(OrderCreateCommand command) {
        log.info("order created id={}", command);
        command.getOrder().setCode("abc");
    }

    @EventListener(OrderCreateCommand.class)
    public void createOrder(OrderCreateCommand command) {
        log.info("{}", command);
    }
}
