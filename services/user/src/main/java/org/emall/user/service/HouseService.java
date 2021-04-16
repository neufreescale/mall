package org.emall.user.service;

import org.emall.user.dao.HouseDao;
import org.emall.user.model.entity.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/4/16
 */
@Service
public class HouseService {

    @Autowired
    private HouseDao houseDao;

    public void insert(House house) {
        houseDao.insert(house);
    }
}
