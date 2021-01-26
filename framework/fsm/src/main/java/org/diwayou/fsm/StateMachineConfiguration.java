package org.diwayou.fsm;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.squirrelframework.foundation.component.SquirrelPostProcessor;
import org.squirrelframework.foundation.component.SquirrelPostProcessorProvider;
import org.squirrelframework.foundation.fsm.StateMachine;

import java.util.Objects;

/**
 * @author gaopeng 2021/1/19
 */
@Configuration
public class StateMachineConfiguration implements SquirrelPostProcessor<StateMachine>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    public StateMachineConfiguration() {
        SquirrelPostProcessorProvider.getInstance().register(StateMachine.class, this);
    }

    @Override
    public void postProcess(StateMachine stateMachine) {
        Objects.requireNonNull(stateMachine);

        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        autowireCapableBeanFactory.autowireBean(stateMachine);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
