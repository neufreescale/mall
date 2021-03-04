package org.emall.order.manager;

import lombok.extern.slf4j.Slf4j;
import org.emall.order.fsm.OrderEvent;
import org.emall.order.fsm.OrderState;
import org.emall.order.fsm.OrderStateMachineFactory;
import org.emall.order.model.command.OrderCommand;
import org.emall.order.model.command.OrderCreateCommand;
import org.emall.order.model.domain.Product;
import org.emall.order.model.entity.Order;
import org.emall.order.thirdparty.user.UserManager;
import org.emall.user.client.dto.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gaopeng 2021/1/18
 */
@Component
@Slf4j
public class OrderManager {

    @Autowired
    private OrderStateMachineFactory orderStateMachineFactory;

    @Autowired
    private UserManager userManager;

    public void create(Buyer buyer, List<Product> products) {
        Order order = new Order();
        order.setId(1L);

        OrderCommand command = new OrderCreateCommand()
                .setLastState(OrderState.Init)
                .setEvent(OrderEvent.Create)
                .setOrder(order);
        orderStateMachineFactory.execute(command);
    }

    @EventListener(OrderCreateCommand.class)
    public void onCreate(OrderCreateCommand command) {
        log.info("order created id={}", command);
    }

    @EventListener(OrderCreateCommand.class)
    public void createOrder(OrderCreateCommand command) {
        log.info("{}", command);
    }
}
