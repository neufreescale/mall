package org.emall.user.api;

import org.apache.dubbo.config.annotation.DubboService;
import org.emall.user.client.api.UserApi;
import org.emall.user.client.dto.ScoreDto;
import org.emall.user.client.dto.UserDto;
import org.emall.user.model.entity.User;
import org.emall.user.service.ScoreService;
import org.emall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author gaopeng 2021/1/29
 */
@DubboService
public class UserApiImpl implements UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private ScoreService scoreService;

    @Override
    public UserDto get(Long id) {
        User user = userService.get(id);

        return user == null ? null : user.to(UserDto.class);
    }

    @Override
    public Collection<ScoreDto> rank(int year) {
        return scoreService.rank(year).stream()
                .map(r -> r.to(ScoreDto.class))
                .collect(Collectors.toList());
    }
}
