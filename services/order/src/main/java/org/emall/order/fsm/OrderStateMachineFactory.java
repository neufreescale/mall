package org.emall.order.fsm;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.fsm.action.PublishContextAsEventAction;
import org.diwayou.fsm.util.ExportUtil;
import org.emall.order.model.command.OrderCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.util.Assert;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;

import javax.annotation.PostConstruct;

/**
 * @author gaopeng 2021/1/20
 */
@Component
@Slf4j
public class OrderStateMachineFactory {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private TransactionOperations transactionOperations;

    private StateMachineBuilder<OrderStateMachine, OrderState, OrderEvent, OrderCommand> builder;

    private PublishContextAsEventAction<OrderStateMachine, OrderState, OrderEvent, OrderCommand> action;

    @PostConstruct
    public void init() {
        action = PublishContextAsEventAction.create(eventPublisher);

        builder = StateMachineBuilderFactory.create(OrderStateMachine.class,
                OrderState.class, OrderEvent.class, OrderCommand.class);

        createTransition(OrderState.Init, OrderState.New, OrderEvent.Create);
        createTransition(OrderState.New, OrderState.Paid, OrderEvent.Pay);
        createTransition(OrderState.Paid, OrderState.Confirmed, OrderEvent.Confirm);
        createTransition(OrderState.Confirmed, OrderState.Processing, OrderEvent.Process);
        createTransition(OrderState.Processing, OrderState.Completed, OrderEvent.Complete);
        createTransition(OrderState.Paid, OrderState.Cancelled, OrderEvent.Cancel);
    }

    private void createTransition(OrderState from, OrderState to, OrderEvent event) {
        builder.externalTransition().from(from).to(to).on(event).perform(action);
    }

    public void execute(OrderCommand command) {
        Assert.state(command != null, "command不能为null");
        Assert.state(command.getLastState() != null, "lastState不能为null");
        Assert.state(command.getEvent() != null, "event不能为null");

        OrderStateMachine fsm = builder.newStateMachine(command.getLastState());

        fsm.fire(command.getEvent(), command);

        command.setCurrentState((OrderState) fsm.getCurrentState());

        // 所有延迟执行都在一个数据库事务中
        if (command.hasDelayExecute()) {
            transactionOperations.executeWithoutResult((status) -> {
                command.runDelayExecute();
            });
        }
    }

    public void export(String filename) {
        OrderStateMachine fsm = builder.newStateMachine(OrderState.Init);

        ExportUtil.toSvg(fsm, filename);
    }
}
