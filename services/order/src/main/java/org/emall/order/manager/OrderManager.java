package org.emall.order.manager;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.cache.KvCache;
import org.emall.order.fsm.OrderStateMachineFactory;
import org.emall.order.thirdparty.user.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gaopeng 2021/1/18
 */
@Component
@Slf4j
public class OrderManager {

    @Autowired
    private OrderStateMachineFactory orderStateMachineFactory;

    @Autowired
    private KvCache kvCache;

    @Autowired
    private UserManager userManager;

    public void create() {
        kvCache.set("test", "1");

        log.info("cache test={}", kvCache.get("test"));

        log.info("user={}", userManager.get(1L));

        orderStateMachineFactory.create();
    }
}
