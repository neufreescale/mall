package org.emall.order.controller;

import org.emall.order.manager.OrderManager;
import org.emall.order.model.domain.Product;
import org.emall.user.client.dto.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * @author gaopeng 2021/1/18
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderManager orderManager;

    @GetMapping("/create")
    public void create(@AuthenticationPrincipal final Buyer buyer) {
        Product product = new Product()
                .setProductId(1L)
                .setSkuId(1L)
                .setSellerId(2L)
                .setShopId(1)
                .setTitle("测试商品")
                .setPrice(BigDecimal.valueOf(100))
                .setQuantity(2);
        orderManager.create(buyer, Collections.singletonList(product));
    }
}
