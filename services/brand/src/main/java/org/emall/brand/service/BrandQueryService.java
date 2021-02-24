package org.emall.brand.service;

import org.apache.commons.lang3.StringUtils;
import org.emall.brand.dao.BrandDao;
import org.emall.brand.dto.BrandSimpleDto;
import org.emall.brand.model.domain.BrandListQuery;
import org.emall.brand.model.domain.BrandSimple;
import org.emall.brand.model.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author gaopeng 2021/2/24
 */
@Service
public class BrandQueryService {

    @Autowired
    private BrandDao brandDao;

    public int countByName(String name) {
        Objects.requireNonNull(name);

        return brandDao.countByName(name);
    }

    public int masterCountByName(String name) {
        return countByName(name);
    }

    public int countByCode(String code) {
        Objects.requireNonNull(code);

        return brandDao.countByCode(code);
    }

    public int masterCountByCode(String code) {
        return countByCode(code);
    }

    public Brand getById(Integer id) {
        Objects.requireNonNull(id);

        return brandDao.getById(id);
    }

    public Brand masterGetById(Integer id) {
        return getById(id);
    }

    public List<Brand> list(BrandListQuery query) {
        Objects.requireNonNull(query);

        return brandDao.list(query);
    }

    public Integer listCount(BrandListQuery query) {
        Objects.requireNonNull(query);

        return brandDao.listCount(query);
    }

    public String getName(Integer id) {
        Objects.requireNonNull(id);

        return brandDao.getName(id);
    }

    public String getLogo(Integer id) {
        Objects.requireNonNull(id);

        return brandDao.getLogo(id);
    }

    public Integer getIdByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        return brandDao.getIdByName(name);
    }

    public BrandSimple getSimple(Integer id) {
        Objects.requireNonNull(id);

        return brandDao.getSimple(id);
    }

    public List<BrandSimpleDto> pageSimple(Integer lastId, Integer size) {
        Objects.requireNonNull(lastId);
        Objects.requireNonNull(size);

        return brandDao.pageSimple(lastId, size);
    }
}
