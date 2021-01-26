package org.emall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaopeng 2021/1/15
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping("/")
    public void index() {

    }
}
