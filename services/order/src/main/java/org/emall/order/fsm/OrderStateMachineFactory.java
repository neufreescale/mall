package org.emall.order.fsm;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import lombok.extern.slf4j.Slf4j;
import org.diwayou.ffsm.EventStateMachineFactory;
import org.emall.order.model.command.OrderCommand;
import org.springframework.stereotype.Component;

/**
 * @author gaopeng 2021/1/20
 */
@Component
@Slf4j
public class OrderStateMachineFactory extends EventStateMachineFactory<OrderState, OrderEvent, OrderCommand> {

    public static final String MACHINE_ID = "OrderStateMachine";

    public OrderStateMachineFactory() {
        super(MACHINE_ID);
    }

    @Override
    protected void buildStateMachine(StateMachineBuilder<OrderState, OrderEvent, OrderCommand> builder) {
        createTransition(OrderState.Init, OrderState.New, OrderEvent.Create);
        createTransition(OrderState.New, OrderState.Paid, OrderEvent.Pay);
        createTransition(OrderState.Paid, OrderState.Confirmed, OrderEvent.Confirm);
        createTransition(OrderState.Confirmed, OrderState.Processing, OrderEvent.Process);
        createTransition(OrderState.Processing, OrderState.Completed, OrderEvent.Complete);
        createTransition(OrderState.Paid, OrderState.Cancelled, OrderEvent.Cancel);
    }
}
