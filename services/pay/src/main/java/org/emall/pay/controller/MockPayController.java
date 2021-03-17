package org.emall.pay.controller;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.core.annotation.IgnoreWrapper;
import org.emall.wechat.pay.request.UnifiedPayRequest;
import org.emall.wechat.pay.response.UnifiedPayResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaopeng 2021/3/18
 */
@RestController
@RequestMapping(value = "/public")
@Slf4j
@IgnoreWrapper
public class MockPayController {

    @RequestMapping(method = RequestMethod.POST, value = "/unifiedOrder")
    public UnifiedPayResponse unifiedOrder(@RequestBody UnifiedPayRequest request) {
        UnifiedPayResponse response = new UnifiedPayResponse();
        response.setPrepayId("456");

        log.info("{}", response);

        return response;
    }
}
