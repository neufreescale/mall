package org.diwayou.security.core;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author gaopeng 2021/3/1
 */
@Getter
public class AuthenticationInfo extends UsernamePasswordAuthenticationToken {

    private final String source;

    private final String token;

    public AuthenticationInfo(String source, String token) {
        super(null, null);
        this.source = source;
        this.token = token;
    }
}
