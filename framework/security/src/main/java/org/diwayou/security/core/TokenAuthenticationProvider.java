package org.diwayou.security.core;

import org.diwayou.security.api.UserRetriever;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @author gaopeng 2021/3/1
 */
public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private List<UserRetriever> userRetrievers;

    public TokenAuthenticationProvider(List<UserRetriever> userRetrievers) {
        this.userRetrievers = userRetrievers;
    }

    @Override
    protected void additionalAuthenticationChecks(final UserDetails d, final UsernamePasswordAuthenticationToken auth) {
        // Nothing to do
    }

    @Override
    protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) {
        if (!(authentication instanceof AuthenticationInfo)) {
            throw new BadCredentialsException("非法请求");
        }

        AuthenticationInfo info = (AuthenticationInfo) authentication;
        for (UserRetriever userRetriever : userRetrievers) {
            if (userRetriever.support(info)) {
                UserDetails userDetails = userRetriever.retrieve(info);
                if (userDetails != null) {
                    return userDetails;
                }
            }
        }

        throw new UsernameNotFoundException(String.format("没有找到用户信息source=%s,token=%s", info.getSource(), info.getToken()));
    }
}
