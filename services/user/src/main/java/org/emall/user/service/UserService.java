package org.emall.user.service;

import org.emall.user.dao.UserDao;
import org.emall.user.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author gaopeng 2021/1/21
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void create() {

    }

    public User get(Long id) {
        Objects.requireNonNull(id);

        return userDao.get(id);
    }
}
