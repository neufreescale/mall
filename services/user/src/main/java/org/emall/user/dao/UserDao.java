package org.emall.user.dao;

import org.apache.ibatis.annotations.Select;
import org.emall.user.model.entity.User;

/**
 * @author gaopeng 2021/1/21
 */
public interface UserDao {

    @Select("select * from user where id=#{id}")
    User get(Long id);
}
