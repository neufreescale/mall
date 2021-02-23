package org.emall.order.model.command;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.experimental.Accessors;
import org.emall.order.fsm.OrderEvent;
import org.emall.order.fsm.OrderState;
import org.emall.order.model.entity.Order;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author gaopeng 2021/1/20
 */
@Data
@Accessors(chain = true)
public abstract class OrderCommand {

    private Order order;

    private OrderEvent event;

    private OrderState lastState;

    private OrderState currentState;

    private final List<Consumer<OrderCommand>> delayExecute = Lists.newArrayList();

    public void delayExecute(Consumer<OrderCommand> execute) {
        delayExecute.add(execute);
    }

    public boolean hasDelayExecute() {
        return !delayExecute.isEmpty();
    }

    public void runDelayExecute() {
        for (Consumer<OrderCommand> execute : delayExecute) {
            execute.accept(this);
        }
    }
}
