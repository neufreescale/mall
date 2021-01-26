package org.emall.order.dao;

import org.apache.ibatis.annotations.Select;
import org.emall.order.model.entity.User;

/**
 * @author gaopeng 2021/1/21
 */
public interface UserDao {

    @Select("select * from user where id=#{id}")
    User get(Integer id);
}
