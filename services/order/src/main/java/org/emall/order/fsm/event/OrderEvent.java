package org.emall.order.fsm.event;

import org.emall.order.model.domain.Order;
import org.springframework.context.ApplicationEvent;

/**
 * @author gaopeng 2021/1/20
 */
public abstract class OrderEvent extends ApplicationEvent {

    public OrderEvent(Order order) {
        super(order);
    }

    @Override
    public Order getSource() {
        return (Order) super.getSource();
    }

    public Long getOrderId() {
        return getSource().getId();
    }
}
