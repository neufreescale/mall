package org.emall.user.manager;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.diwayou.core.exception.CustomException;
import org.emall.user.model.domain.SchoolInfo;
import org.emall.user.model.domain.SyncStatus;
import org.emall.user.model.entity.School;
import org.emall.user.model.entity.Score;
import org.emall.user.service.SchoolService;
import org.emall.user.service.ScoreSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gaopeng 2021/5/6
 */
@Component
@Slf4j
public class SchoolManager {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private ScoreSyncService scoreSyncService;

    @Autowired
    private ScoreSyncManager scoreSyncManager;

    public void upload(List<SchoolInfo> schoolInfos) {
        for (SchoolInfo schoolInfo : schoolInfos) {
            School school = parse(schoolInfo);

            schoolService.insert(school);
        }
    }

    private School parse(SchoolInfo schoolInfo) {
        School school = schoolInfo.to(School.class);


        return school
                .setId(Integer.parseInt(schoolInfo.getId()))
                .setF985(numToBoolean(schoolInfo.getF985()))
                .setF211(numToBoolean(schoolInfo.getF211()))
                .setSync(SyncStatus.NO_SYNC.getId());
    }

    private static Boolean numToBoolean(String num) {
        if ("1".equals(num)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public void sync(Integer schoolId, int year) {
        School school = schoolService.get(schoolId);
        if (school == null) {
            throw new CustomException("没有找到学校信息");
        }

        List<Score> scores = scoreSyncManager.sync(school, year);

        if (CollectionUtils.isEmpty(scores)) {
            throw new CustomException("同步学校信息失败");
        }

        scoreSyncService.save(scores, school, year);
    }

    public void syncAll() {
        List<School> schools = schoolService.allNoSync();

        for (School school : schools) {
            List<Score> allScore = Lists.newArrayList();
            for (int year = 2020; year > 2017; year--) {
                List<Score> scores = scoreSyncManager.sync(school, year);

                if (CollectionUtils.isEmpty(scores)) {
                    continue;
                }

                allScore.addAll(scores);
            }

            if (CollectionUtils.isEmpty(allScore)) {
                log.warn("拉取学校信息为空 {}", school);
                schoolService.setSync(school.getId(), SyncStatus.FAIL);
            } else {
                scoreSyncService.save(allScore, school);
            }
        }
    }
}
