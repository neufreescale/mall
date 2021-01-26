package org.emall.order.fsm;

import lombok.extern.slf4j.Slf4j;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * @author gaopeng 2021/1/20
 */
@Slf4j
@StateMachineParameters(stateType = OrderState.class, eventType = OrderEvent.class, contextType = Integer.class)
public class OrderStateMachine extends AbstractUntypedStateMachine {

    protected void fromAToB(OrderState from, OrderState to, OrderEvent event, Integer context) {
        log.info("Transition from '{}' to '{}' on event '{}' with context '{}'.", from, to, event, context);
    }

    protected void ontoB(OrderState from, OrderState to, OrderEvent event, Integer context) {
        log.info("Entry State '{}'.", to);
    }
}
