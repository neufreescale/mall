package org.diwayou.security.api;

import org.diwayou.security.core.AuthenticationInfo;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author gaopeng 2021/3/2
 */
public interface UserRetriever {

    boolean support(AuthenticationInfo info);

    UserDetails retrieve(AuthenticationInfo info);
}
