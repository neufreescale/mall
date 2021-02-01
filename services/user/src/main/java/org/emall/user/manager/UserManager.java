package org.emall.user.manager;

import org.diwayou.cache.annotation.Cache;
import org.emall.user.model.entity.User;
import org.emall.user.model.response.UserResponse;
import org.emall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gaopeng 2021/2/1
 */
@Component
public class UserManager {

    @Autowired
    private UserService userService;

    @Cache(key = "user")
    public UserResponse get(Long id) {
        User user = userService.get(id);

        return user == null ? null : user.to(UserResponse.class);
    }
}
