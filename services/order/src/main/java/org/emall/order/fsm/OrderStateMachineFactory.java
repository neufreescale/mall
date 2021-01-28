package org.emall.order.fsm;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.fsm.action.PublishContextAsEventAction;
import org.emall.order.fsm.event.OrderConfirmEvent;
import org.emall.order.fsm.event.OrderEventEnum;
import org.emall.order.fsm.state.OrderState;
import org.emall.order.model.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

import javax.annotation.PostConstruct;

/**
 * @author gaopeng 2021/1/20
 */
@Component
@Slf4j
public class OrderStateMachineFactory {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private UntypedStateMachineBuilder builder;

    @PostConstruct
    public void init() {
        builder = StateMachineBuilderFactory.create(OrderStateMachine.class);
        builder.externalTransition()
                .from(OrderState.Init)
                .to(OrderState.Confirmed)
                .on(OrderEventEnum.Confirm)
                .perform(PublishContextAsEventAction.create(eventPublisher));
        builder.onEntry(OrderState.Attention).callMethod("ontoB");
    }

    public void create() {
        UntypedStateMachine fsm = builder.newStateMachine(OrderState.Init);

        Order order = new Order();
        order.setId(1L);
        OrderConfirmEvent confirmEvent = new OrderConfirmEvent(order);
        fsm.fire(OrderEventEnum.Confirm, confirmEvent);

        log.info("cur state {}", fsm.getCurrentState());
    }

    @EventListener(OrderConfirmEvent.class)
    public void onCreate(OrderConfirmEvent event) {
        log.info("order created id={}", event.getOrderId());
    }
}
