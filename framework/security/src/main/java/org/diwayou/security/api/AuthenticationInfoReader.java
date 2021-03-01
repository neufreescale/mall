package org.diwayou.security.api;

import org.diwayou.security.core.AuthenticationInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gaopeng 2021/3/1
 */
public interface AuthenticationInfoReader {

    AuthenticationInfo read(HttpServletRequest request);
}
