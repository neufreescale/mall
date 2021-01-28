package org.emall.order.fsm.event;

import org.emall.order.model.domain.Order;

/**
 * @author gaopeng 2021/1/28
 */
public class OrderCanceledEvent extends OrderEvent {

    public OrderCanceledEvent(Order order) {
        super(order);
    }
}
