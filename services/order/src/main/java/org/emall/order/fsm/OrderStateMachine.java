package org.emall.order.fsm;

import org.emall.order.model.command.OrderCommand;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

/**
 * @author gaopeng 2021/1/20
 */
public class OrderStateMachine extends AbstractStateMachine<OrderStateMachine, OrderState, OrderEvent, OrderCommand> {
}
