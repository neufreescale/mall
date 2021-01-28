package org.emall.order.thirdparty.user;

import org.apache.dubbo.config.annotation.DubboReference;
import org.emall.user.client.api.UserApi;
import org.emall.user.client.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author gaopeng 2021/1/29
 */
@Component
public class UserManager {

    @DubboReference
    private UserApi userApi;

    public UserDto get(Long id) {
        Objects.requireNonNull(id);

        return userApi.get(id);
    }
}
