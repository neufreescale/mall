package org.emall.user.service;

import org.emall.user.dao.SchoolDao;
import org.emall.user.model.domain.SyncStatus;
import org.emall.user.model.entity.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gaopeng 2021/5/6
 */
@Service
public class SchoolService {

    @Autowired
    private SchoolDao schoolDao;

    public void insert(School school) {
        schoolDao.insert(school);
    }

    public School get(Integer id) {
        return schoolDao.get(id);
    }

    public void setSync(Integer id, SyncStatus syncStatus) {
        schoolDao.setSync(id, syncStatus.getId());
    }

    public List<School> allNoSync() {
        return schoolDao.allNoSync();
    }
}
