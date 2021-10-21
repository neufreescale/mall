package org.emall.order.fsm;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.diwayou.ffsm.action.PublishContextAsEventAction;
import org.diwayou.ffsm.util.ExportUtil;
import org.emall.order.model.command.OrderCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * @author gaopeng 2021/1/20
 */
@Component
@Slf4j
public class OrderStateMachineFactory {

    private static final String MACHINE_ID = "OrderStateMachine";

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private TransactionOperations transactionOperations;

    private StateMachineBuilder<OrderState, OrderEvent, OrderCommand> builder;

    private StateMachine<OrderState, OrderEvent, OrderCommand> stateMachine;

    private PublishContextAsEventAction<OrderState, OrderEvent, OrderCommand> action;

    @PostConstruct
    public void init() {
        action = PublishContextAsEventAction.create(eventPublisher);

        builder = StateMachineBuilderFactory.create();

        createTransition(OrderState.Init, OrderState.New, OrderEvent.Create);
        createTransition(OrderState.New, OrderState.Paid, OrderEvent.Pay);
        createTransition(OrderState.Paid, OrderState.Confirmed, OrderEvent.Confirm);
        createTransition(OrderState.Confirmed, OrderState.Processing, OrderEvent.Process);
        createTransition(OrderState.Processing, OrderState.Completed, OrderEvent.Complete);
        createTransition(OrderState.Paid, OrderState.Cancelled, OrderEvent.Cancel);

        stateMachine = builder.build(MACHINE_ID);
    }

    private void createTransition(OrderState from, OrderState to, OrderEvent event) {
        builder.externalTransition().from(from).to(to).on(event).perform(action);
    }

    public void execute(OrderCommand command) {
        Assert.state(command != null, "command不能为null");
        Assert.state(command.getLastState() != null, "lastState不能为null");
        Assert.state(command.getEvent() != null, "event不能为null");

        OrderState currentState = stateMachine.fireEvent(command.getLastState(), command.getEvent(), command);

        command.setCurrentState(currentState);

        // 所有延迟执行都在一个数据库事务中
        if (command.hasDelayExecute()) {
            transactionOperations.executeWithoutResult((status) -> {
                command.runDelayExecute();
            });
        }
    }

    public void export(String filename) {
        ExportUtil.toSvg(stateMachine, filename);
    }
}
