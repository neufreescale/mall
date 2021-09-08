package org.emall.user.client.api;

import org.emall.user.client.dto.ScoreDto;
import org.emall.user.client.dto.UserDto;

import java.util.Collection;

/**
 * @author gaopeng 2021/1/29
 */
public interface UserApi {

    UserDto get(Long id);

    Collection<ScoreDto> rank(int year);
}
