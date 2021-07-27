package org.diwayou.core.controller;

import org.diwayou.core.result.ResultWrapper;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gaopeng 2021/1/19
 */
@RestController
public class CustomErrorController extends AbstractErrorController {

    public CustomErrorController() {
        super(new DefaultErrorAttributes());
    }

    @RequestMapping(path = "/error")
    public ResultWrapper handle(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus httpStatus = getStatus(request);
        response.setStatus(httpStatus.value());

        return ResultWrapper.fail(httpStatus.getReasonPhrase());
    }
}
