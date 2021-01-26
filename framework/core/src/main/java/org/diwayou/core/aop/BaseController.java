package org.diwayou.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.core.exception.CustomException;
import org.diwayou.core.result.ResultWrapper;
import org.diwayou.core.util.WebRequestUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * @author gaopeng 2021/1/13
 */
@ControllerAdvice
@Slf4j
public class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"), true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResultWrapper exceptionHandler(HttpServletRequest request, Exception exception) {
        if (exception instanceof CustomException) {
            return ResultWrapper.fail(exception.getMessage());
        }

        log.error("{} - {} - {} - {}",
                this.getClass().getName(),
                request.getRequestURI(),
                WebRequestUtil.getIp(request),
                request.getHeader("User-Agent"),
                exception);

        if (exception instanceof BindException) {
            Optional<String> message = ((BindException) exception).getBindingResult().getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .findFirst();

            return ResultWrapper.fail(message.orElse("参数绑定错误!"));
        }

        if (exception instanceof MethodArgumentNotValidException) {
            Optional<String> message = ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .findFirst();

            return ResultWrapper.fail(message.orElse("参数绑定错误!"));
        }

        return ResultWrapper.fail();
    }
}
