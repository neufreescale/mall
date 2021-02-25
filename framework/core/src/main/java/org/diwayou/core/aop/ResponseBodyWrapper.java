package org.diwayou.core.aop;

import org.diwayou.core.annotation.IgnoreWrapper;
import org.diwayou.core.result.ResultWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author gaopeng 2021/1/15
 */
// 由于Swagger原因，这个地方必须限制包范围，要不然会影响到Swagger
@ControllerAdvice(value = {"org.emall"})
public class ResponseBodyWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getMethodAnnotation(IgnoreWrapper.class) == null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return ResultWrapper.success();
        }

        if (body instanceof ResultWrapper) {
            return body;
        }

        return ResultWrapper.success(body);
    }
}
