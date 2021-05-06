package org.emall.user.service;

import org.emall.user.model.domain.SyncStatus;
import org.emall.user.model.entity.School;
import org.emall.user.model.entity.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gaopeng 2021/5/6
 */
@Service
public class ScoreSyncService {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private ScoreService scoreService;

    public void save(List<Score> scores, School school, int year) {
        scoreService.delete(school.getId(), year);
        scoreService.insert(scores);

        schoolService.setSync(school.getId(), SyncStatus.SYNC);
    }

    public void save(List<Score> scores, School school) {
        scoreService.deleteSchool(school.getId());
        scoreService.insert(scores);

        schoolService.setSync(school.getId(), SyncStatus.SYNC);
    }
}
