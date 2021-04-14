package org.emall.order.controller;

import org.emall.order.manager.OrderManager;
import org.emall.order.model.request.OrderCreateRequest;
import org.emall.order.model.response.OrderCreateResponse;
import org.emall.user.client.dto.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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
    public OrderCreateResponse create(@AuthenticationPrincipal final Buyer buyer, @Validated OrderCreateRequest createRequest) {
        OrderCreateRequest request = new OrderCreateRequest();
        request.setPhone("12366666666");
        return orderManager.create(buyer, request);
    }

    @GetMapping("/export")
    public void export() {
        orderManager.export();
    }
}
