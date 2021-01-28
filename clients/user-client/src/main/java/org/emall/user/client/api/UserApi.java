package org.emall.user.client.api;

import org.emall.user.client.dto.UserDto;

/**
 * @author gaopeng 2021/1/29
 */
public interface UserApi {

    UserDto get(Long id);
}
