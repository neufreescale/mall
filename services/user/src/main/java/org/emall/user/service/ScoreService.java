package org.emall.user.service;

import org.emall.user.dao.ScoreDao;
import org.emall.user.model.entity.Score;
import org.emall.user.model.response.ScoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author gaopeng 2021/5/6
 */
@Service
public class ScoreService {

    @Autowired
    private ScoreDao scoreDao;

    public void insert(Score score) {
        scoreDao.insert(score);
    }

    public void insert(List<Score> scores) {
        for (Score score : scores) {
            insert(score);
        }
    }

    public void delete(Integer id, int year) {
        scoreDao.delete(id, year);
    }

    public void deleteSchool(Integer id) {
        scoreDao.deleteSchool(id);
    }

    public Collection<ScoreResponse> rank(Integer year) {
        return scoreDao.rank(year);
    }
}
