package org.emall.order.manager;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.config.ConfigApi;
import org.emall.order.fsm.OrderEvent;
import org.emall.order.fsm.OrderState;
import org.emall.order.fsm.OrderStateMachineFactory;
import org.emall.order.model.command.OrderCommand;
import org.emall.order.model.command.OrderCreateCommand;
import org.emall.order.model.command.OrderPaidCommand;
import org.emall.order.model.entity.Order;
import org.emall.order.model.request.OrderCreateRequest;
import org.emall.order.model.response.OrderCreateResponse;
import org.emall.order.thirdparty.user.UserManager;
import org.emall.order.validation.Insert;
import org.emall.order.validation.Update;
import org.emall.pay.mq.PaidMessage;
import org.emall.user.client.dto.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @author gaopeng 2021/1/18
 */
@Component
@Slf4j
@Validated(Insert.class)
public class OrderManager {

    @Autowired
    private OrderStateMachineFactory orderStateMachineFactory;

    @Autowired
    private UserManager userManager;

    @Validated(Update.class)
    public OrderCreateResponse create(Buyer buyer, @Valid OrderCreateRequest request) {
        Order order = new Order();
        order.setId(1L);
        log.info("{}", ConfigApi.env().env());

        OrderCommand command = new OrderCreateCommand()
                .setLastState(OrderState.Init)
                .setEvent(OrderEvent.Create)
                .setOrder(order);
        orderStateMachineFactory.execute(command);

        return new OrderCreateResponse();
    }

    public void paid(PaidMessage message) {
        Order order = new Order();
        order.setId(1L);

        OrderCommand command = new OrderPaidCommand()
                .setLastState(OrderState.New)
                .setEvent(OrderEvent.Pay)
                .setOrder(order);
        orderStateMachineFactory.execute(command);
    }

    public void export() {
        orderStateMachineFactory.export("order");
    }
}
