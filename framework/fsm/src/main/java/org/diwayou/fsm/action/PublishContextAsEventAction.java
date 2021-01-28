package org.diwayou.fsm.action;

import org.springframework.context.ApplicationEventPublisher;
import org.squirrelframework.foundation.fsm.Action;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

/**
 * @author gaopeng 2021/1/28
 */
public class PublishContextAsEventAction implements Action<UntypedStateMachine, Object, Object, Object> {

    private ApplicationEventPublisher eventPublisher;

    public static PublishContextAsEventAction create(ApplicationEventPublisher eventPublisher) {
        return new PublishContextAsEventAction(eventPublisher);
    }

    private PublishContextAsEventAction(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void execute(Object from, Object to, Object event, Object context, UntypedStateMachine stateMachine) {
        eventPublisher.publishEvent(context);
    }

    @Override
    public String name() {
        return getClass().getName();
    }

    @Override
    public int weight() {
        return 0;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public long timeout() {
        return 0;
    }
}
