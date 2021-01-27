package org.emall.order.manager;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.config.IConfig;
import org.emall.order.fsm.OrderStateMachineFactory;
import org.emall.order.model.entity.User;
import org.emall.order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gaopeng 2021/1/18
 */
@Component
@Slf4j
public class OrderManager {

    @Autowired
    private IConfig config;

    @Autowired
    private OrderStateMachineFactory orderStateMachineFactory;

    @Autowired
    private UserService userService;

    public void create() {
        User user = userService.get(1);

        userService.create();

        log.info("{}", user);
    }
}
