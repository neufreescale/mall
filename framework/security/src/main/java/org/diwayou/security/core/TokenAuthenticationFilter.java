package org.diwayou.security.core;

import org.diwayou.core.spi.ServiceRegister;
import org.diwayou.security.api.AuthenticationInfoReader;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gaopeng 2021/3/1
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    static {
        ServiceRegister.registerSingleton(AuthenticationInfoReader.class);
    }

    private final AuthenticationInfoReader provider = ServiceRegister.getOneSingleton(AuthenticationInfoReader.class);

    public TokenAuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

    @Override
    public Authentication attemptAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response) {
        final AuthenticationInfo auth = provider.read(request);

        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
