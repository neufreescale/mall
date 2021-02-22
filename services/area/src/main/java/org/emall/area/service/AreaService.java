package org.emall.area.service;

import org.emall.area.dao.AreaDao;
import org.emall.area.model.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gaopeng 2021/2/23
 */
@Service
public class AreaService {

    @Autowired
    private AreaDao areaDao;

    public List<Area> getByParentId(Integer parentId) {
        return areaDao.getByParentId(parentId);
    }

    public Area getByCode(String code) {
        return areaDao.getByCode(code);
    }
}
