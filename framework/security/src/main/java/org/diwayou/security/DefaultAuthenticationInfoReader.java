package org.diwayou.security;

import org.apache.commons.lang3.StringUtils;
import org.diwayou.security.api.AuthenticationInfoReader;
import org.diwayou.security.core.AuthenticationInfo;
import org.springframework.security.authentication.BadCredentialsException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gaopeng 2021/3/1
 */
public class DefaultAuthenticationInfoReader implements AuthenticationInfoReader {

    private static final String SOURCE = "source_";
    private static final String TOKEN = "t_";

    @Override
    public AuthenticationInfo read(HttpServletRequest request) {
        String source = get(request, SOURCE);
        if (StringUtils.isBlank(source)) {
            throw new BadCredentialsException("Missing source_");
        }

        String token = get(request, TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new BadCredentialsException("Missing t_");
        }

        return new AuthenticationInfo(source, token);
    }

    private String get(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        if (StringUtils.isBlank(value)) {
            value = request.getParameter(name);
        }

        return value;
    }
}
