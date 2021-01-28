package org.emall.order.fsm;

import lombok.extern.slf4j.Slf4j;
import org.emall.order.fsm.event.OrderEventEnum;
import org.emall.order.fsm.state.OrderState;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * @author gaopeng 2021/1/20
 */
@Slf4j
@StateMachineParameters(stateType = OrderState.class, eventType = OrderEventEnum.class, contextType = Object.class)
public class OrderStateMachine extends AbstractUntypedStateMachine {

    protected void ontoB(OrderState from, OrderState to, OrderEventEnum event, Object context) {
        log.info("Entry State '{}'.", to);
    }
}
