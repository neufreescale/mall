package org.emall.user.api;

import org.apache.dubbo.config.annotation.DubboService;
import org.emall.user.client.api.UserApi;
import org.emall.user.client.dto.UserDto;
import org.emall.user.model.entity.User;
import org.emall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gaopeng 2021/1/29
 */
@DubboService
public class UserApiImpl implements UserApi {

    @Autowired
    private UserService userService;

    @Override
    public UserDto get(Long id) {
        User user = userService.get(id);

        return user == null ? null : user.to(UserDto.class);
    }
}
