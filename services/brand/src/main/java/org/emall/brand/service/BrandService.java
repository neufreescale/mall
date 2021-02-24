package org.emall.brand.service;

import org.emall.brand.dao.BrandDao;
import org.emall.brand.model.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/2/24
 */
@Service
public class BrandService {

    @Autowired
    private BrandDao brandDao;

    public void create(Brand brand) {
        brandDao.insert(brand);
    }

    public int update(Brand brand) {
        return brandDao.update(brand);
    }
}
