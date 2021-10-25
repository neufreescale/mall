package org.diwayou.ffsm;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import org.apache.commons.lang3.StringUtils;
import org.diwayou.ffsm.action.PublishContextAsEventAction;
import org.diwayou.ffsm.util.ExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * @author gaopeng 2021/10/25
 */
public abstract class EventStateMachineFactory<S, E, C> {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private StateMachineBuilder<S, E, C> builder;

    private StateMachine<S, E, C> stateMachine;

    protected PublishContextAsEventAction<S, E, C> action;

    private final String machineId;

    public EventStateMachineFactory(String machineId) {
        Assert.isTrue(StringUtils.isNotBlank(machineId), "machineId不能为空");

        this.machineId = machineId;
    }

    @PostConstruct
    public void init() {
        action = PublishContextAsEventAction.create(eventPublisher);

        builder = StateMachineBuilderFactory.create();

        buildStateMachine(builder);

        stateMachine = builder.build(machineId);
    }

    protected void createTransition(S from, S to, E event) {
        builder.externalTransition().from(from).to(to).on(event).perform(action);
    }

    protected abstract void buildStateMachine(StateMachineBuilder<S, E, C> builder);

    public S fireEvent(S state, E event, C context) {
        Assert.state(state != null, "state不能为null");
        Assert.state(event != null, "event不能为null");
        Assert.state(context != null, "context不能为null");

        return stateMachine.fireEvent(state, event, context);
    }

    public StateMachine<S, E, C> getStateMachine() {
        return stateMachine;
    }

    public void export(String filename) {
        ExportUtil.toSvg(stateMachine, filename);
    }
}
