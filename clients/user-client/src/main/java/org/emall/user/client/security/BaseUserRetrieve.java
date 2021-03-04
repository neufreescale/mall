package org.emall.user.client.security;

import org.diwayou.security.api.UserRetriever;
import org.diwayou.security.core.AuthenticationInfo;
import org.emall.user.client.dto.Buyer;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author gaopeng 2021/3/2
 */
public class BaseUserRetrieve implements UserRetriever {

    @Override
    public boolean support(AuthenticationInfo info) {
        return "base".equals(info.getSource());
    }

    @Override
    public UserDetails retrieve(AuthenticationInfo info) {
        if ("test".equals(info.getToken())) {
            return new Buyer()
                    .setId(1L)
                    .setName("diwayou");
        }

        return null;
    }
}
