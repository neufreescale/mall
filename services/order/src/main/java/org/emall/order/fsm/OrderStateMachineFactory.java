package org.emall.order.fsm;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.fsm.util.ExportUtil;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

import javax.annotation.PostConstruct;

/**
 * @author gaopeng 2021/1/20
 */
@Component
@Slf4j
public class OrderStateMachineFactory {

    private UntypedStateMachineBuilder builder;

    @PostConstruct
    public void init() {
        builder = StateMachineBuilderFactory.create(OrderStateMachine.class);
        builder.externalTransition().from(OrderState.Init).to(OrderState.Confirmed).on(OrderEvent.Confirm).callMethod("fromAToB");
        builder.onEntry(OrderState.Attention).callMethod("ontoB");
    }

    public void create() {
        UntypedStateMachine fsm = builder.newStateMachine(OrderState.Init);
        fsm.fire(OrderEvent.Confirm, 10);

        log.info("cur state {}", fsm.getCurrentState());

        ExportUtil.toSvg(fsm, "order");
    }
}
