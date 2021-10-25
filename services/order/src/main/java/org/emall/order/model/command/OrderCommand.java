package org.emall.order.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.diwayou.ffsm.AbstractContext;
import org.emall.order.fsm.OrderEvent;
import org.emall.order.fsm.OrderState;
import org.emall.order.model.entity.Order;

/**
 * @author gaopeng 2021/10/25
 */
@EqualsAndHashCode(callSuper = false)
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public abstract class OrderCommand extends AbstractContext<OrderState, OrderEvent> {

    private Order order;
}
