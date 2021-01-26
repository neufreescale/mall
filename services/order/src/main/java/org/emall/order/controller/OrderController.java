package org.emall.order.controller;

import org.emall.order.manager.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaopeng 2021/1/18
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderManager orderManager;

    @GetMapping("/create")
    public void create() {
        orderManager.create();
    }
}
