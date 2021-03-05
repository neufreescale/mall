package org.emall.pay.controller;

import org.emall.pay.manager.PayManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaopeng 2021/3/5
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayManager payManager;

    @GetMapping("/create")
    public void create() {
        payManager.create();
    }
}
