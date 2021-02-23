package org.diwayou.fsm.action;

import org.springframework.context.ApplicationEventPublisher;
import org.squirrelframework.foundation.fsm.Action;
import org.squirrelframework.foundation.fsm.StateMachine;

/**
 * @author gaopeng 2021/1/28
 */
public class PublishContextAsEventAction<T extends StateMachine<T, S, E, C>, S, E, C> implements Action<T, S, E, C> {

    private ApplicationEventPublisher eventPublisher;

    public static <T extends StateMachine<T, S, E, C>, S, E, C> PublishContextAsEventAction<T, S, E, C> create(ApplicationEventPublisher eventPublisher) {
        return new PublishContextAsEventAction<>(eventPublisher);
    }

    private PublishContextAsEventAction(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void execute(S from, S to, E event, C context, T stateMachine) {
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
