package org.diwayou.security.core;

import org.diwayou.core.result.ResultCode;
import org.diwayou.core.result.ResultWrapper;
import org.diwayou.core.util.WebResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gaopeng 2021/3/2
 */
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        WebResponseUtil.writeJson(response, ResultWrapper.fail(ResultCode.NotLogin));
    }
}
