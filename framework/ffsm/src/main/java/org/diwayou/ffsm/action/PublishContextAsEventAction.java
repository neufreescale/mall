package org.diwayou.ffsm.action;

import com.alibaba.cola.statemachine.Action;
import org.diwayou.ffsm.AbstractContext;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author gaopeng 2021/10/21
 */
public class PublishContextAsEventAction<S, E, C> implements Action<S, E, C> {

    private final ApplicationEventPublisher eventPublisher;

    public static <S, E, C> PublishContextAsEventAction<S, E, C> create(ApplicationEventPublisher eventPublisher) {
        return new PublishContextAsEventAction<>(eventPublisher);
    }

    private PublishContextAsEventAction(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void execute(S from, S to, E event, C context) {
        if (context instanceof AbstractContext) {
            AbstractContext<S, E>  ctx = (AbstractContext<S, E>) context;

            ctx.setFrom(from);
            ctx.setTo(to);
            ctx.setEvent(event);
        }

        eventPublisher.publishEvent(context);
    }
}
